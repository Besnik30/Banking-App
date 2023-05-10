package com.beso.repository;

import com.beso.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query(value = "SELECT t.transaction_id,t.amount,t.currency,t.transaction_type,t.account_id\n" +
            "FROM transaction AS t\n" +
            " JOIN account AS a\n" +
            "   ON t.account_id=a.account_id\n" +
            " JOIN user AS u\n" +
            "   ON a.client_id=u.user_id\n" +
            "WHERE u.user_id= ?1",nativeQuery = true)
    Page<Transaction> showClientTransactions(Integer id, Pageable pageable);

}
