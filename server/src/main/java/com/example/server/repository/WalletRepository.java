package com.example.server.repository;

import com.example.server.entity.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT w FROM Wallet w where w.id=:id")
    Wallet getWalletByIdWithLocking(Long id);


    @Query(value = "SELECT amount FROM WALLET WHERE id=:id", nativeQuery = true)
    Long getAmountById(Long id);
}
