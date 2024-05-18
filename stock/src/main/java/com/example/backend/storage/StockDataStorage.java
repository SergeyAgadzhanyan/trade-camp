package com.example.backend.storage;

import com.example.backend.model.StockData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StockDataStorage extends JpaRepository<StockData, Long> {
    List<StockData> findByNameAndDateBetween(String name, LocalDateTime from, LocalDateTime to, Sort sort);

    @Query(value = "SELECT * from stock_data as s where name = :name offset(SELECT floor(random() *((select count(st" +
            ".id) from " +
            "stock_data" +
            " " +
            "as st) - :sum" +
            "+ 1))) limit :sum", nativeQuery = true)
    List<StockData> getRandomData(String name, int sum);
}
