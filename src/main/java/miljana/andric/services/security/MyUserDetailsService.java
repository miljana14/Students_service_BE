package miljana.andric.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miljana.andric.entities.RoleEnum;
import miljana.andric.entities.UserEntity;
import miljana.andric.repositories.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findById(username);
        user.orElseThrow(() -> new UsernameNotFoundException("user " + username + " ne postoji!"));
        MyUserDetails userDetails = new MyUserDetails(user.get().getUsername(), user.get().getPassword(), user.get().getFirstName(), user.get().getLastName(), user.get().getRole());
        if(userDetails.getRole().equals(RoleEnum.ROLE_STUDENT) || userDetails.getRole().equals(RoleEnum.ROLE_PROFESSOR)) {
        	if (user.get().isNewUser()) {
                userDetails.setNewUser(true);
            } else {
                userDetails.setNewUser(false);
            }

        }
       
        return userDetails;
    }
}
