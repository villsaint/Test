package ru.villsaint.villsaint.service;

import org.springframework.stereotype.Service;
import ru.villsaint.villsaint.models.Role;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.repo.RoleRepo;

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

    public void setRoles(User user, String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (int i = 0; i < role.length; i++) {
            if (role[i].equals("ROLE_ADMIN")) {
                roleSet.add(findAll().get(0));
            }
            if (role[i].equals("ROLE_USER")) {
                roleSet.add(findAll().get(1));
            }
        }
        user.setRoles(roleSet);
    }
}
