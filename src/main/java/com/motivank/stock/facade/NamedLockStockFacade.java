package com.motivank.stock.facade;

import com.motivank.stock.repository.StockRepository;
import com.motivank.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final StockRepository stockRepository;

    private final StockService stockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        String key = "stock_" + id;

        try {
            stockRepository.getLock(key);
            stockService.decreaseWithNamedLock(id, quantity);
        } finally {
            stockRepository.releaseLock(key);
        }
    }

}
