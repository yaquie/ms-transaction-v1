package com.example.transaction.demo.transaction.service;

import com.example.transaction.demo.transaction.model.Transaction;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService{

  @Override
  public Mono<Map<String, Double>> getTransaction(List<Transaction> transactions) {
    return Mono.just(
        transactions.stream()
            .filter( t -> t.getAmount() > 100)
            .collect(Collectors.groupingBy(
                Transaction:: getType)
            )).flatMap(trx ->  {
              Map<String, Double> result = getAmountByType(trx);
              return Mono.just(result);
    });
  }

  public static Map<String, Double> getAmountByType(Map<String, List<Transaction>> transactions){
    return transactions
        .entrySet()
        .stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> entry.getValue().stream()
                .mapToDouble( t -> t.getAmount() + t.getAmount()*0.05)
                .sum()
        ));
  }

}
