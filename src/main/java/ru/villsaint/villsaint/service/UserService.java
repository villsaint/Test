package ru.villsaint.villsaint.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.villsaint.villsaint.config.SecurityConfig;
import ru.villsaint.villsaint.models.Role;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleService roleService;

    public UserService(UserRepo userRepo,RoleService roleService) {

        this.userRepo = userRepo;
        this.roleService = roleService;
//        firstUser();
    }

    public User findById(long id) {
        return userRepo.getOne(id);
    }
    public User findByUsername(String username){
       return userRepo.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null){
            throw  new UsernameNotFoundException(String.format("User '%s' not found",username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), user.getAuthorities());
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
    @Transactional
    public void saveUser(User user) {
        String password = user.getPassword();
        if (!((password.startsWith("$2a$") || password.startsWith("$2b$")) && password.length() == 60)) {
            user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        }
        userRepo.save(user);
    }
    
    @Transactional
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @PostConstruct
    private void defaultUser(){
        createDefaultUser("admin","default",28,"admin@mail.ru","admin","admin",1L,"ROLE_ADMIN");
        createDefaultUser("user","default",28,"user@mail.ru","user","user",2L,"ROLE_USER");
    }
    private void createDefaultUser(String name, String lastname, int age,String email,
                                    String username, String password,Long idRole,String role){
        User defaultAdmin = new User(name,lastname,age,email,username,
                SecurityConfig.passwordEncoder().encode(password));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(idRole,role));
        defaultAdmin.setRoles(roleSet);
        saveUser(defaultAdmin);
    }

}
