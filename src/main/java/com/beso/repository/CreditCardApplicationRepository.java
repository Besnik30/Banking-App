package com.beso.repository;

import com.beso.entity.CreditCardApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CreditCardApplicationRepository extends JpaRepository<CreditCardApplication,Integer> {

    @Query(value = "SELECT * FROM credit_card_application WHERE application_status = ?1",nativeQuery = true)
    List<CreditCardApplication> getApplicationsByStatus(String status);
}
