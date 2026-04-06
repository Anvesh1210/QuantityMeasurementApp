package com.app.quantitymeasurement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.quantitymeasurement.model.AppUserEntity;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

	Optional<AppUserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByMobileNumber(String mobileNumber);
}
