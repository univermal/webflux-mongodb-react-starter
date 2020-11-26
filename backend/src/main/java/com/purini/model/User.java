package com.purini.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purini.model.pojos.AuditData;
import com.purini.model.pojos.Auditable;
import com.purini.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "user")
public class User implements UserDetails, Auditable {

    @Id
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private List<Role> roles;
    private AuditData auditData;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

}