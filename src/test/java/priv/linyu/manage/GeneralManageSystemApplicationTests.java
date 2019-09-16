package priv.linyu.manage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.linyu.manage.pojo.User;
import priv.linyu.manage.service.IRoleService;
import priv.linyu.manage.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneralManageSystemApplicationTests {

    @Autowired
    private IRoleService roleService;

    /**
     * 测试用户是否正在
     */
    @Test
    public void testCheckUser(){
        roleService.saveRoleResource(4,17);
    }

    @Test
    public void contextLoads() {
    }

}
