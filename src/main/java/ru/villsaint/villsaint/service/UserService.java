package ru.villsaint.villsaint.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.repo.UserRepo;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
        userRepo.save(user);
    }
    
    @Transactional
    public void deleteUser(User user) {
        userRepo.delete(user);
    }


}
