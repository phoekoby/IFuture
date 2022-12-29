package com.example.server.service.impl;

import com.example.server.entity.Wallet;
import com.example.server.repository.WalletRepository;
import com.example.server.service.BalanceService;
import com.hazelcast.map.IMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    WalletRepository walletRepository;

    IMap<Long, Long> balanceCache;

    @Override
    public Optional<Long> getBalance(Long id) {
        Long amount = balanceCache.getOrDefault(id, walletRepository.getAmountById(id));
        return Optional.ofNullable(balanceCache.put(id, amount, 10, TimeUnit.MINUTES));
    }

    @Override
    @Async("threadPoolTaskExecutor")
    @Transactional
    public void changeBalance(Long id, Long amount) {
        Wallet wallet = walletRepository.getWalletByIdWithLocking(id);
        wallet.setAmount(amount + wallet.getAmount());
        wallet = walletRepository.save(wallet);
        balanceCache.put(id, wallet.getAmount(), 10, TimeUnit.MINUTES);
    }
}
