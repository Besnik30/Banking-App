package com.beso.repository;

import com.beso.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * FROM User  WHERE user_type = ?1",nativeQuery = true)
    Page<User> showUsersByUserType(String userType, Pageable pageable);

    @Query(value = "SELECT * FROM User WHERE user_name= ?1",nativeQuery = true)
   Optional<User> getUserByUsername(String username);

}
