package me.project.registerloginsystem.service;

import me.project.registerloginsystem.entity.Users;
import me.project.registerloginsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = repo.findByUsername(username);
        if (users.isPresent()) {
            var userObj = users.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(Users users) {
        if (users.getRoles() == null) {
            return new String[]{"USER"};
        }
        return users.getRoles().split(",");
    }

    public Users save(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return repo.save(users);
    }

    public List<Users> findAll() {
        return repo.findAll();
    }
//
//    public User read(int id) {
//        User user = userRepository.findById(id);
//        if (user == null) {
//            throw new NotFoundException("User Not Found");
//        }
//        return user;
//    }

    public Users update(Users users) {
        Optional<Users> optionalUser = repo.findById(users.getId());

        Users exist = optionalUser.get();
        exist.setUsername(users.getUsername());
        exist.setPassword(passwordEncoder.encode(users.getPassword()));
        exist.setRoles(users.getRoles());

        return repo.save(exist);
    }


    public void delete(int id) {
        repo.deleteById(id);
    }

}
