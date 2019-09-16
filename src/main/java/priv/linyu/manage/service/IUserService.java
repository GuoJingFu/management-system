package priv.linyu.manage.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import priv.linyu.manage.pojo.User;

/**
 * @className: IUserService
 * @author: QiuShangLin
 * @description: 用户业务逻辑接口
 * @date: 2019/7/18 22:26
 * @version: 1.0
 **/
public interface IUserService {

    /**
     * 根据用户名查询用户
     * @param username  用户名
     * @return
     */
    User findByUsername(String username);


    /**
     * 分页查询全部数据
     * @param specification
     * @param pageable
     * @return
     */
    Page<User> findByPage(Specification<User> specification, Pageable  pageable);

    /**
     * 新增 / 修改
     * @param user
     */
    void saveOrUpdate(User user);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

}
