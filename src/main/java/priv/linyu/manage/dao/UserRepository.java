package priv.linyu.manage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import priv.linyu.manage.pojo.User;

/**
 * @className: UserRepository
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/18 22:10
 * @version: 1.0
 **/
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查询用户
     * @param username  用户名
     * @return
     */
    @Query(value = "select userid,username,`password`,roleid from sys_user where username=?",nativeQuery = true)
    User findByUsername(String username);

}
