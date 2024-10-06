package com.tradecamp.user.repository;


import com.tradecamp.user.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
    @Query("select t from trade_history as t where t.user.name = :userName order by t.id desc limit 1")
    Optional<TradeHistory> findLast(String userName);
}
