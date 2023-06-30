package com.java.amazoncloneapi.Repository;

import com.java.amazoncloneapi.Entity.SignUpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpUserRepository extends JpaRepository<SignUpUser,Long> {
  SignUpUser findByEmail(String email);
}
