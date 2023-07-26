package ua.lviv.iot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.lviv.iot.model.Role;
import ua.lviv.iot.model.User;
import ua.lviv.iot.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                mapToGrantedAuthority(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapToGrantedAuthority(List<Role>roles){
        return roles.stream()
                .map((i)->new SimpleGrantedAuthority(i.getName())).collect(Collectors.toList());
    }
}
