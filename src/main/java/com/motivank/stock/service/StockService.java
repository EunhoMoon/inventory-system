package com.motivank.stock.service;

import com.motivank.stock.domain.Stock;
import com.motivank.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;

    //    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        // Stock 조회
        // 재고 감소
        // 갱신된 값을 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
    }

    @Transactional
    public void decreaseWithPessimisticLock(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);
        stock.decrease(quantity);
    }

    @Transactional
    public void decreaseWithOptimisticLock(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void decreaseWithNamedLock(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void decreaseWithRedisLock(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
    }

}
