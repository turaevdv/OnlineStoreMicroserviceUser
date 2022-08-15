package ru.turaev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.turaev.user.enums.Role;
import ru.turaev.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByIdAndRoleIs(long id, Role role);
    List<User> findAllByRoleIs(Role role);
    boolean existsByUsername(String username);
}
