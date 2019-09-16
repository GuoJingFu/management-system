package priv.linyu.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @className: RoleResource
 * @author: QiuShangLin
 * @description: 角色和资源中间表
 * @date: 2019/7/18 0018 0:30
 * @version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role_resource")
public class RoleResource {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer roleresourceid;

    /**
     *
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roleid", foreignKey = @ForeignKey(name = "none"))
    private Role role;

    /**
     *
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resourceid",foreignKey = @ForeignKey(name = "none"))
    private Resource resource;

}
