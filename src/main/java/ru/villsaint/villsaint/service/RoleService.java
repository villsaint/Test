package ru.villsaint.villsaint.service;

import org.springframework.stereotype.Service;
import ru.villsaint.villsaint.models.Role;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.repo.RoleRepo;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }


    @PostConstruct
    private void addRoles(){
        roleRepo.save(new Role(1L,"ROLE_ADMIN"));
        roleRepo.save(new Role(2L,"ROLE_USER"));
    }
}
