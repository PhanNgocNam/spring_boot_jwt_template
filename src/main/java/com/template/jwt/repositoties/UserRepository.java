package com.template.jwt.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import com.template.jwt.models.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
