package priv.linyu.manage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import priv.linyu.manage.pojo.Role;

import java.util.List;

/**
 * @className: IRoleService
 * @author: QiuShangLin
 * @description: 角色业务逻辑接口
 * @date: 2019/7/19 0019 21:27
 * @version: 1.0
 **/
public interface IRoleService {

    /**
     * 查询全部角色信息
     * @return
     */
    List<Role> findAll();


    /**
     * 分页查询全部数据
     * @param specification
     * @param pageable
     * @return
     */
    Page<Role> findByPage(Specification<Role> specification, Pageable pageable);

    /**
     * 添加角色-资源
     * @param roleId
     * @param resourceId
     */
    void saveRoleResource(Integer roleId, Integer resourceId);

    /**
     * 删除角色-资源
     * @param roleId
     */
    void deleteRoleResource(Integer roleId);

    /**
     * 根据角色id查询用户id
     * @param roleid
     * @return
     */
    List<Integer> findUserByRoleid(Integer roleid);

    /**
     * 根据角色id查询角色
     * @param roleid
     * @return
     */
    Role findRoleByRoleid(Integer roleid);

    /**
     * 根据角色名字查询
     * @param rolename
     * @return
     */
    Role findByRolename(String rolename);

    /**
     * 保存
     * @param role
     */
    void save(Role role);


    /**
     * 删除
     * @param role
     */
    void delete(Role role);

}
