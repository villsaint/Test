package ru.villsaint.villsaint.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.villsaint.villsaint.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

}
