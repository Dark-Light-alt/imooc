package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 账号
 */
@Data
@TableName("account_number")
public class AccountNumber implements UserDetails {

    // 账号 id
    @TableId("account_number_id")
    private String accountNumberId;

    // 用户名
    @TableField
    private String username;

    // 密码
    @TableField
    private String password;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 最后登录时间
    @TableField("end_login_time")
    private Date endLoginTime;

    // 是否锁定：0未锁定 1锁定
    @TableField
    private Integer islocked;

    // 员工 id
    @TableField("employee_id")
    private String employeeId;

    // 权限
    @TableField(exist = false)
    private List<GrantedAuthority> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getPermissions();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getIslocked() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
