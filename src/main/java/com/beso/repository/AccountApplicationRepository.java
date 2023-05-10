package com.beso.repository;

import com.beso.entity.AccountApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AccountApplicationRepository extends JpaRepository<AccountApplication,Integer> {
    @Query(value = "SELECT * FROM account_application WHERE client_id = ?1",nativeQuery = true)
    List<AccountApplication> showApplicationsByClientId(Integer clientId);

    @Query(value = "SELECT * FROM account_application WHERE application_status = ?1",nativeQuery = true)
    Page<AccountApplication> showAccountApplicationsByStatus(String status, Pageable pageable);
}
