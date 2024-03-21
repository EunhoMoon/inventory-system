package com.motivank.stock.service;

import com.motivank.stock.domain.Stock;
import com.motivank.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @BeforeEach
    public void before() {
        stockRepository.save(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    @DisplayName("재고 감소가 정상 동작한다.")
    public void decrease() {
        // given
        stockService.decrease(1L, 10L);

        // when
        Stock stock = stockRepository.findById(1L).orElseThrow();

        // then
        assertThat(stock.getQuantity()).isEqualTo(90L);
    }

    @Test
    @DisplayName("재고가 0개 미만이 되면 예외가 발생한다.")
    public void decreaseException() {
        // expect
        assertThatThrownBy(() -> stockService.decrease(1L, 101L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("재고는 0개 미만이 될 수 없습니다.");
    }

}