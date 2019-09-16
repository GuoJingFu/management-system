package priv.linyu.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.linyu.manage.dao.ResourceRepository;
import priv.linyu.manage.pojo.Resource;
import priv.linyu.manage.service.IResourceService;

import java.util.List;

/**
 * @className: ResourceServiceImpl
 * @author: QiuShangLin
 * @description: 资源业务逻辑接口实现类
 * @date: 2019/7/19 1:05
 * @version: 1.0
 **/
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * 查询全部资源
     * @return
     */
    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    /**
     * 根据资源父编号查询
     * @param resourcepid
     * @return
     */
    @Override
    public List<Resource> findByResourcepid(Integer resourcepid) {
        return resourceRepository.findByResourcepid(resourcepid);
    }

    /**
     * 根据用户id查询资源地址
     * @param userid
     * @return
     */
    @Override
    public List<String> findUrlByUserid(Integer userid) {
        return resourceRepository.findUrlByUserid(userid);
    }

    /**
     * 根据用户id和资源父id查询资源列表
     * @param userid
     * @param resourcepid
     * @return
     */
    @Override
    public List<Resource> findResourceByUserid(Integer userid, Integer resourcepid) {
        return resourceRepository.findResourceByUserid(userid,resourcepid);
    }

    /**
     * 根据角色id查询
     * @param roleid
     * @return
     */
    @Override
    public List<Integer> findByRoleid(Integer roleid) {
        return resourceRepository.findByRoleid(roleid);
    }


}
