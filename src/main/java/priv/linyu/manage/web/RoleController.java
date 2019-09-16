package priv.linyu.manage.web;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.linyu.manage.pojo.Role;
import priv.linyu.manage.service.IResourceService;
import priv.linyu.manage.service.IRoleService;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

/**
 * @className: RoleController
 * @author: QiuShangLin
 * @description: 角色视图控制器
 * @date: 2019/7/19 0019 21:30
 * @version: 1.0
 **/
@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IResourceService resourceService;

    /**
     * 打开角色页面
     * @return
     */
    @RequestMapping(value = "/view")
    public String view(){
        return "role";
    }


    /**
     * 查询全部类别信息
     * @return
     */
    @RequestMapping(value = "/findAll.do")
    @ResponseBody
    public Map<String, Object> findAll(){
        Map<String, Object> map = Maps.newHashMap();
        map.put("success",true);
        map.put("list",roleService.findAll());

        return map;
    }


    /**
     * 查询列表信息
     * @param searchcontent 查询内容
     * @param page          页数
     * @param rows          每页记录数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "searchcontent", required = false) String searchcontent,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) {

        // 创建查询规格对象
        Specification<Role> specification = (Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = null;
            Path path = null;

            if (StringUtils.isNotBlank(searchcontent)){
                path = root.get("rolename");
                predicate = cb.like(path,"%" + searchcontent + "%");
            }

            return predicate;
        };

        Pageable pageable = PageRequest.of(page -1,rows, Sort.Direction.ASC,"roleid");
        Page<Role> pageModel = roleService.findByPage(specification,pageable);

        List<Role> list = pageModel.getContent();
        Long count = pageModel.getTotalElements();

        Map<String, Object> map = Maps.newHashMap();
        map.put("total", count);
        map.put("rows", list);
        map.put("success", true);

        return map;
    }

    /**
     * 根据角色id查询资源
     * @param roleid
     * @return
     */
    @RequestMapping("/resource/match")
    @ResponseBody
    public List<Integer> match(@RequestParam(value = "roleid",required = false)String roleid){
        List<Integer> list = resourceService.findByRoleid(Integer.parseInt(roleid));
        return list;
    }

    /**
     * 设置资源,先删除再设置
     * @param resourceids
     * @param roleid
     * @return
     */
    @RequestMapping(value = "/relResources")
    @ResponseBody
    public Map<String, Object> relResources(@RequestParam(value = "roleid",required = false)String roleid,
                                            @RequestParam(value = "resourceids",required = false)String resourceids){
        Map<String, Object> map = Maps.newHashMap();
        try {
            roleService.deleteRoleResource(Integer.parseInt(roleid));
            String[] resourceidArr = resourceids.split(",");
            for (String resourceid : resourceidArr) {
                roleService.saveRoleResource(Integer.parseInt(roleid),Integer.parseInt(resourceid));
            }
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error(e.getMessage());
        }

        return map;
    }


    /**
     * 执行新增操作
     * @param role
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String,Object> save(Role role){
        Map<String,Object> map = Maps.newHashMap();
        Role curr = roleService.findByRolename(role.getRolename());
        System.out.println(curr);
        if (curr == null){
            try {
                roleService.save(role);
                map.put("flag", "ok");
            }catch (Exception e){
                log.error(e.getMessage());
                map.put("flag", "ng");
            }
        }else {
            map.put("flag", "exist");
        }

        return map;
    }

    /**
     * 执行删除操作
     * @param roleid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam("roleid") Integer roleid){
        Map<String,Object> map = Maps.newHashMap();
        try {
            List<Integer> list = roleService.findUserByRoleid(roleid);
            if (CollectionUtils.isEmpty(list)){
                map.put("success", "该角色有关联的用户，无法删除");
            }else {
                Role role = roleService.findRoleByRoleid(roleid);
                roleService.delete(role);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            map.put("success", "false");
        }

        return map;
    }


}
