package priv.linyu.manage.service;

import priv.linyu.manage.pojo.Resource;

import java.util.List;

/**
 * @className: IResourceService
 * @author: QiuShangLin
 * @description: 资源业务逻辑接口
 * @date: 2019/7/19 1:04
 * @version: 1.0
 **/
public interface IResourceService {

    /**
     * 查询全部资源
     * @return
     */
    List<Resource> findAll();


    /**
     * 根据资源父编号查询
     * @param resourcepid
     * @return
     */
    List<Resource> findByResourcepid(Integer resourcepid);


    /**
     * 根据用户id查询资源地址
     * @param userid
     * @return
     */
    List<String> findUrlByUserid(Integer userid);


    /**
     * 根据用户id和资源父id查询资源列表
     * @param userid
     * @param resourcepid
     * @return
     */
    List<Resource> findResourceByUserid(Integer userid, Integer resourcepid);


    /**
     * 根据角色id查询
     * @param roleid
     * @return
     */
    List<Integer> findByRoleid(Integer roleid);

}
