package priv.linyu.manage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import priv.linyu.manage.pojo.Resource;

import java.util.List;

/**
 * @className: ResourceRepository
 * @author: QiuShangLin
 * @description: 资源Repository
 * @date: 2019/7/19 1:03
 * @version: 1.0
 **/
public interface ResourceRepository extends JpaRepository<Resource, Integer>, JpaSpecificationExecutor<Resource> {

    /**
     * 根据资源父编号查询
     * @param resourcepid  资源父编号
     * @return
     */
    @Query(value = "select resourceid,resourcepid,resourcename,resourceicon,resourceurl from sys_resource where resourcepid = ?",nativeQuery = true)
    List<Resource> findByResourcepid(Integer resourcepid);

    /**
     * 根据用户id查询资源地址列表
     * @param userid  用户id
     * @return
     */
    @Query(value = "select rs.resourceurl FROM sys_role_resource AS rr\n" +
            "INNER JOIN sys_resource AS rs ON rr.resourceid = rs.resourceid\n" +
            "INNER JOIN sys_role AS r ON rr.roleid = r.roleid\n" +
            "INNER JOIN sys_user AS u ON u.roleid = r.roleid\n" +
            "WHERE u.userid = :userid",nativeQuery = true)
    List<String> findUrlByUserid(@Param(value = "userid")Integer userid);


    /**
     * 根据用户id和资源父id查询资源列表
     * @param userid  用户id
     * @param resourcepid  资源父id
     * @return
     */
    @Query(value = "SELECT rs.* FROM sys_role_resource AS rr " +
            "INNER JOIN sys_resource AS rs ON rr.resourceid = rs.resourceid " +
            "INNER JOIN sys_role AS r ON rr.roleid = r.roleid " +
            "INNER JOIN sys_user AS u ON u.roleid = r.roleid " +
            "WHERE u.userid = :userid AND rs.resourcepid = :resourcepid", nativeQuery = true)
    List<Resource> findResourceByUserid(@Param("userid") Integer userid, @Param("resourcepid") Integer resourcepid);


    /**
     * 根据角色id查询
     * @param roleid  角色id
     * @return
     */
    @Query(value = "SELECT rr.`resourceid` FROM sys_role_resource AS rr\n" +
            "INNER JOIN sys_resource AS rs ON rr.`resourceid` = rs.`resourceid` AND rr.`roleid` = :roleid", nativeQuery = true)
    List<Integer> findByRoleid(@Param("roleid") Integer roleid);


}
