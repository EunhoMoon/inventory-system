package com.motivank.stock.service;

import com.motivank.stock.domain.Stock;
import com.motivank.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        // Stock 조회
        // 재고 감소
        // 갱신된 값을 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
    }

}
