package com.motivank.stock.facade;

import com.motivank.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long id, Long quantity) {
        RLock lock = redissonClient.getLock(id.toString());

        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("Lock 획득 실패");
                return;
            }

            stockService.decreaseWithRedisLock(id, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
