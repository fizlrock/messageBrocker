package com.fizlrock.denly.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fizlrock.denly.Domain.User;

/**
 * UserRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findByUsername(String username);

}
