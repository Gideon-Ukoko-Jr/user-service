package com.project.userservice.repositories;

import com.project.userservice.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    Optional<UserEntity> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
