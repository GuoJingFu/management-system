package priv.linyu.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import priv.linyu.manage.dao.RoleRepository;
import priv.linyu.manage.pojo.Role;
import priv.linyu.manage.service.IRoleService;

import java.util.List;


/**
 * @className: RoleServiceImpl
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/19 0019 21:28
 * @version: 1.0
 **/
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 查询全部角色信息
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * 分页查询全部数据
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<Role> findByPage(Specification<Role> specification, Pageable pageable) {
        return roleRepository.findAll(specification,pageable);
    }

    /**
     * 添加角色-资源
     * @param roleId
     * @param resourceId
     */
    @Override
    public void saveRoleResource(Integer roleId, Integer resourceId) {
        roleRepository.saveRoleResource(roleId,resourceId);
    }

    /**
     * 删除角色-资源
     * @param roleId
     */
    @Override
    public void deleteRoleResource(Integer roleId) {
        roleRepository.deleteRoleResource(roleId);
    }

    /**
     * 根据角色id查询用户id
     * @param roleid
     * @return
     */
    @Override
    public List<Integer> findUserByRoleid(Integer roleid) {
        return roleRepository.findUserByRoleid(roleid);
    }


    /**
     * 根据角色id查询角色
     * @param roleid
     * @return
     */
    @Override
    public Role findRoleByRoleid(Integer roleid) {
        return roleRepository.findRoleByRoleid(roleid);
    }

    /**
     * 根据角色名字查询
     * @param rolename
     * @return
     */
    @Override
    public Role findByRolename(String rolename) {
        return roleRepository.findByRolename(rolename);
    }

    /**
     * 新增
     * @param role
     */
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    /**
     * 删除
     * @param role
     */
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
