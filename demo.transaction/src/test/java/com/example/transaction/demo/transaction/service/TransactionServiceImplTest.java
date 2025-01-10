package com.example.transaction.demo.transaction.service;

import com.example.transaction.demo.transaction.Utils;
import com.example.transaction.demo.transaction.model.Transaction;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @InjectMocks
  private TransactionServiceImpl transactionService;

  static List<Transaction> transactions;
  @BeforeAll
  static void setup() throws IOException {
    transactions = Utils
        .convertToListFromJson("listTransactions.json", Transaction.class);

  }

  @Test
  @DisplayName("return amount by transference type")
  void returnAmountByTransferenceType(){
    Map<String, Double> expectedAmoutn = new HashMap<>();
    expectedAmoutn.put("WITHDRAW",315.0);
    expectedAmoutn.put("DEPOSIT",367.5);

    Mono<Map<String, Double>> responseTransacion = transactionService.getTransaction(transactions);
    StepVerifier.create(responseTransacion)
        .expectNextMatches( result -> {
          return expectedAmoutn.equals(result);
        })
        .verifyComplete();
  }

  @Test
  @DisplayName("return empty body when no pass the threshold")
  void returnEmptyBodyWhenNoPassTheThreshold() throws IOException {
    List<Transaction> transactions = Utils
        .convertToListFromJson("listTransactionsAountlessthanThreshold.json",
            Transaction.class);
    Map<String, Double> expectedAmoutn = new HashMap<>();


    Mono<Map<String, Double>> responseTransacion = transactionService.getTransaction(transactions);
    StepVerifier.create(responseTransacion)
        .expectNextMatches( result -> {
          return expectedAmoutn.equals(result);
        })
        .verifyComplete();
  }

  @Test
  @DisplayName("return empty response when send an empty list")
  void returnEmptyResponseWhenSendAnEmptyList(){
    List<Transaction> transactionList = Arrays.asList();
    Map<String, Double> expectedEmptyMap = new HashMap<>();

    Mono<Map<String, Double>> responseTransacion = transactionService.getTransaction(transactionList);
    StepVerifier.create(responseTransacion)
        .expectNextMatches( result -> {
          return expectedEmptyMap.equals(result);
        })
        .verifyComplete();
  }

  @Test
  @DisplayName("return bad request when no send a body")
  void returnBadRequestWhenNoSendABody(){
    List<Transaction> transactionList = Arrays.asList();
    Map<String, Double> expectedEmptyMap = new HashMap<>();

    Mono<Map<String, Double>> responseTransacion = transactionService.getTransaction(transactionList);
    StepVerifier.create(responseTransacion)
        .expectNextMatches( result -> {
          return expectedEmptyMap.equals(result);
        })
        .verifyComplete();
  }
}