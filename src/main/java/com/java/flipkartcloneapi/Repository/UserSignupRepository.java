package com.java.flipkartcloneapi.Repository;

import com.java.flipkartcloneapi.Entity.SignUpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSignupRepository extends JpaRepository<SignUpUser,Long> {
  SignUpUser findByEmail(String email);
}
