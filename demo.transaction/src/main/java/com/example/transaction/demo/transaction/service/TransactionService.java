package com.example.transaction.demo.transaction.service;

import com.example.transaction.demo.transaction.model.Transaction;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

public interface TransactionService {

  public Mono<Map<String, Double>> getTransaction (List<Transaction> transactions);
}
