package vinhit.authservice.service.ServiceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vinhit.authservice.repository.UserRepository;
import vinhit.authservice.service.JwtService;

@Component
public class UserDetailServiceImp implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username);
    }
}
