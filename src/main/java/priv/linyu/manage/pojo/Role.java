package priv.linyu.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @className: Role
 * @author: QiuShangLin
 * @description: 角色实体类
 * @date: 2019/7/18 0018 0:22
 * @version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role")
public class Role {

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer roleid;

    /**
     * 角色名称
     */
    @Column
    private String rolename;

}
