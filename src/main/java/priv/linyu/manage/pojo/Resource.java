package priv.linyu.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @className: Resource
 * @author: QiuShangLin
 * @description: 资源实体类
 * @date: 2019/7/18 0018 0:26
 * @version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_resource")
public class Resource {

    /**
     * 资源编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceid;

    /**
     * 资源父编号
     */
    @Column
    private Integer resourcepid;

    /**
     * 资源名称
     */
    @Column
    private String resourcename;

    /**
     * 资源图标
     */
    @Column
    private String resourceicon;

    /**
     * 资源URL
     */
    @Column
    private String resourceurl;

    /**
     * 资源子节点集合
     */
    @OneToMany(cascade = {CascadeType.ALL})
    @Transient
    private List<Resource> children;
}
