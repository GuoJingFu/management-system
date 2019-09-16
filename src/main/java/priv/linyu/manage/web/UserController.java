package priv.linyu.manage.web;
import	java.util.List;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import priv.linyu.manage.pojo.Resource;
import priv.linyu.manage.pojo.User;
import priv.linyu.manage.service.IResourceService;
import priv.linyu.manage.service.IUserService;

import javax.persistence.criteria.*;
import java.util.Map;

/**
 * @className: UserController
 * @author: QiuShangLin
 * @description: 用户视图控制器
 * @date: 2019/7/18 22:31
 * @version: 1.0
 **/
@Controller
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IResourceService resourceService;

    /**
     * 打开登录页面
     * @return
     */
    @GetMapping(value = "/admin/login.html")
    public String toLogin(){
        return "login";
    }


    /**
     * 打开用户界面
     * @return
     */
    @GetMapping(value = "/user/view")
    public String view(){
        return "user";
    }

    /**
     * 执行登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Map<String,Object> doLogin(User user){

        Map<String,Object> map = Maps.newHashMap();

        // 获取主体
        Subject subject = SecurityUtils.getSubject();

        // 设置token令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {

            if (!subject.isAuthenticated()){
                System.out.println("默认subject.isAuthenticated()Boolean值为："+subject.isAuthenticated());
                subject.login(token);

                map.put("flag","ok");
            }

        }catch (Exception e){
            if (StringUtils.isNotEmpty(e.getMessage())){
                log.error(e.getMessage());
            }
            map.put("flag","ng");
        }
        return map;
    }


    /**
     * 登录成功后所跳转到首页
     * @param map
     * @return
     */
    @RequestMapping(value = "/index.html")
    public String index(Map<String,Object> map){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        map.put("currentuser",user.getUsername());
        return "index";
    }


    /**
     * 根据父级id加载资源
     * @param resourcepid
     * @return
     */
    @RequestMapping("/loadResource")
    @ResponseBody
    public List<Resource> loadResources(@RequestParam(value = "resourcepid")Integer resourcepid){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Resource> parents = resourceService.findResourceByUserid(user.getUserid(),resourcepid);
        if (parents.size() > 0){
            parents.forEach(resource -> {
                List<Resource> children = resourceService.findResourceByUserid(user.getUserid(),resource.getResourceid());
                resource.setChildren(children);
            });
        }
        return parents;
    }

    /**
     * 查询列表信息
     * @param searchcondition 查询条件
     * @param searchcontent   查询内容
     * @param page            页数
     * @param rows            每页记录数
     * @return
     */
    @RequestMapping(value = "/user/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "searchcondition", required = false) String searchcondition,
                                    @RequestParam(value = "searchcontent", required = false) String searchcontent,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows){

        // 创建查询规格对象
        Specification<User> specification = (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = null;
            Path path = null;

            if (searchcondition != null && !"".equals(searchcondition) && searchcontent != null && !"".equals(searchcontent)){
                switch (searchcondition) {
                    case "username":      // 明细名称
                        path = root.get("username");
                        predicate = criteriaBuilder.like(path, "%" + searchcontent + "%");
                        break;
                    case "rolename":      // 类别名称
                        path = root.join("role", JoinType.INNER);
                        predicate = criteriaBuilder.like(path.get("rolename"), "%" + searchcontent + "%");
                        break;
                    default:
                        break;
                }
            }
            return predicate;
        };

        // 封装分页对象
        Pageable pageable = PageRequest.of(page - 1,rows, Sort.Direction.ASC,"userid");
        Page<User> pageModel = userService.findByPage(specification,pageable);

        // 获取分页的数据
        List<User> list = pageModel.getContent();
        // 获取分页的总记录数
        Long count = pageModel.getTotalElements();

        Map<String, Object> map = Maps.newHashMap();
        map.put("total",count);
        map.put("rows",list);
        map.put("success",true);

        return map;
    }


    /**
     * 新增 / 修改
     * @return
     */
    @RequestMapping(value = "/user/save")
    @ResponseBody
    public Map<String, Object> save(User user){
        Map<String, Object> map = Maps.newHashMap();
        try {
            userService.saveOrUpdate(user);
            map.put("flag", "ok");
        }catch (Exception e){
            log.error(e.getMessage());
            map.put("flag", "ng");
        }
        return map;
    }

    /**
     * 根据id删除
     * @param userid
     * @return
     */
    @RequestMapping(value = "/user/delete")
    @ResponseBody
    public Map<String, Object> deleteById(@RequestParam(value = "userid") String userid){
        Map<String, Object> map = Maps.newHashMap();
        userService.deleteById(Integer.parseInt(userid));
        map.put("success",true);
        return map;
    }


}
