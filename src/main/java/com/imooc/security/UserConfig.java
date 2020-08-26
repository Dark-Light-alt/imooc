package com.imooc.security;

import com.imooc.entity.AccountNumber;
import com.imooc.service.impl.AccountNumberServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户详情实现
 */
@Component
public class UserConfig implements UserDetailsService {

    @Resource
    private AccountNumberServiceImpl iAccountNumberServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 模拟角色和用户权限
        List<GrantedAuthority> permissions = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");

        AccountNumber accountNumber = iAccountNumberServiceImpl.findByUsername(s);

        System.out.println(accountNumber);

        if (null == accountNumber) {
            throw new UsernameNotFoundException("用户不存在");
        }

        accountNumber.setPermissions(permissions);

        return accountNumber;
    }
}
