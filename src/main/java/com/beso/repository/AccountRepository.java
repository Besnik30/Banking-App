package com.beso.repository;

import com.beso.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query(value="SELECT account_id FROM account a WHERE a.iban  = ?1",nativeQuery = true)
    Integer findAccountIdByIban(String iban);

    @Query(value="SELECT account_id FROM account a WHERE a.client_id = ?1",nativeQuery = true)
    List<Integer> findAccountIdsByClientId(Integer clientId);

    @Query(value = "SELECT * FROM account WHERE account_status= ?1",nativeQuery = true)
    Page<Account> findAccountsByStatus(String status, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findById(Integer id);
}
