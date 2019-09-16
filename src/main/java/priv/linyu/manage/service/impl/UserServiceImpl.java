package priv.linyu.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import priv.linyu.manage.dao.UserRepository;
import priv.linyu.manage.pojo.User;
import priv.linyu.manage.service.IUserService;

/**
 * @className: UserServiceImpl
 * @author: QiuShangLin
 * @description: 用户业务逻辑接口实现类
 * @date: 2019/7/18 22:30
 * @version: 1.0
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据用户名查询用户
     * @param username  用户名
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 分页查询全部数据
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<User> findByPage(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification,pageable);
    }

    /**
     * 新增 / 修改
     * @param user
     */
    @Override
    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
