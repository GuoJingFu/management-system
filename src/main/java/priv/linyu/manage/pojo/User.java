package priv.linyu.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @className: User
 * @author: QiuShangLin
 * @description: 用户实体类
 * @date: 2019/7/18 0018 0:19
 * @version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_user")
public class User {

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,columnDefinition = "COMMENT '人员编号'")
    private Integer userid;

    /**
     * 用户名
     */
    @Column(columnDefinition = "COMMENT '用户名'")
    private String username;

    /**
     * 密码
     */
    @Column(columnDefinition = "COMMENT '密码'")
    private String password;

    /**
     *
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "roleid", foreignKey = @ForeignKey(name = "none"))
    private Role role;
}
