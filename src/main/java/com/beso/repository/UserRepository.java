package com.beso.repository;

import com.beso.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM User  WHERE user_type = ?1",nativeQuery = true)
    List<User> showUsersByUserType(String userType);
}
