package com.lhy.campusswap.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lhy.campusswap.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ClassName: UserDetailsImpl
 * Package: com.lhy.campusswap.security
 * Description:
 *
 * @Author LHY
 * @Create 2025/11/30 23:02
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String phone;
    private String username;
    private String nickname;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();


    // 在构造时设置
    public AuthUser(User user) {
        this.userId = user.getId();
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        //创建一个optional对象，如果user.getRoles()为空，optional对象的值为List.of()，否则为user.getRoles()的值
        //Optional.ofNullable() 只是将可能为 null 的值包装起来，真正处理 null 的是 orElse() 方法。这样设计的好处是让 null 处理变得显式和强制，提高了代码的安全性和可读性
        this.roles = Optional.ofNullable(user.getRoles()).orElse(List.of());
        this.permissions = Optional.ofNullable(user.getPermissions()).orElse(List.of());
    }


    @Override
    /**
     * getAuthorities() 方法返回的 Collection 类型属性是一个"只读属性"（只有 getter 方法没有对应的 setter 方法），Jackson 不知道如何处理这种类型的反序列化。
     * 这个注解告诉 Jackson 在序列化和反序列化过程中忽略 getAuthorities() 方法，这样就不会尝试处理这个"setterless"属性了。
     * 1、authorities 是计算属性：实际上 authorities 是从 permissions 计算得出的，不需要单独存储
     * 2、Spring Security 会重新计算：当 AuthUser 从 Redis 中取出后，Spring Security 会在需要时调用 getAuthorities() 方法重新获取权限信息
     * 3、避免序列化问题：通过忽略这个属性，我们绕过了 Jackson 在处理 setterless 属性时的限制
     */
      @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security 只需要权限字符串即可
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return null; } // 我们不在 AuthUser 里存密码
    @Override
    public String getUsername() { return phone != null ? phone : username; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    // 方便业务代码直接判断
    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}