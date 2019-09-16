package priv.linyu.manage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.linyu.manage.pojo.Resource;
import priv.linyu.manage.service.IResourceService;
import priv.linyu.manage.util.ConvertTreeUtil;

import java.util.List;
import java.util.Map;

/**
 * @className: ResourceController
 * @author: QiuShangLin
 * @description: 资源视图控制器
 * @date: 2019/7/19 0019 1:12
 * @version: 1.0
 **/
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 打开资源列表页
     * @return
     */
    @RequestMapping("/view")
    public String view() {
        return "resource";
    }

    /**
     * 加载树资源
     * @return
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<Map<String,Object>> tree(){
        List<Resource> resources = resourceService.findAll();
        return ConvertTreeUtil.convert2Tree(resources,0,"resourcepid", "resourceid", "resourcename");
    }


    /**
     * 加载树资源列表
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Map<String,Object>> list(){
        List<Resource> list = resourceService.findAll();
        return ConvertTreeUtil.convert2TreeGrid(list, 0, "resourcepid", "resourceid", "resourcename", "resourceicon", "resourceurl");
    }

}
