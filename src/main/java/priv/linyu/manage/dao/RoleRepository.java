package priv.linyu.manage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import priv.linyu.manage.pojo.Role;

import java.util.List;

/**
 * @className: RoleRepository
 * @author: QiuShangLin
 * @description: 角色Repository
 * @date: 2019/7/19 0019 20:35
 * @version: 1.0
 **/
public interface RoleRepository extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role> {

    /**
     * 添加角色-资源
     * @param roleId
     * @param resourceId
     */
    @Query(value = "INSERT INTO sys_role_resource (roleid,resourceid) VALUES (?1,?2)", nativeQuery = true)
    void saveRoleResource(Integer roleId, Integer resourceId);

    /**
     * 删除角色-资源
     * @param roleId
     */
    @Query(value = "DELETE FROM sys_role_resource WHERE roleid= ?1 ", nativeQuery = true)
    void deleteRoleResource(Integer roleId);

    /**
     * 根据角色id查询用户id
     * @param roleid
     * @return
     */
    @Query(value = "select userid from sys_user_role where roleid=?1", nativeQuery = true)
    List<Integer> findUserByRoleid(Integer roleid);

    /**
     * 根据角色id查询角色
     * @param roleid
     * @return
     */
    @Query(value = "select * from sys_role where roleid = ?1", nativeQuery = true)
    Role findRoleByRoleid(Integer roleid);

    /**
     * 根据角色名字查询
     * @param rolename
     * @return
     */
    @Query(value = "select * from sys_role where rolename=?1 ", nativeQuery = true)
    Role findByRolename(String rolename);

}
