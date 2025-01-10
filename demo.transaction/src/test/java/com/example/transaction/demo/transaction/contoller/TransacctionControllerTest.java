package com.example.transaction.demo.transaction.contoller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.transaction.demo.transaction.Utils;
import com.example.transaction.demo.transaction.model.Transaction;
import com.example.transaction.demo.transaction.service.TransactionService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TransacctionControllerTest {

  @InjectMocks
  private TransacctionController transacctionController;

  @Mock
  private TransactionService transactionService;

  static List<Transaction> transactionList;

  @BeforeAll
  static void setup() throws IOException {
    transactionList = Utils
        .convertToListFromJson("listTransactions.json", Transaction.class);

  }

  @Test
  @DisplayName("return amount by transference type")
  void returnAmountByTransferenceType(){
    Map<String, Double> expectedAmoutn = new HashMap<>();
    expectedAmoutn.put("WITHDRAW",315.0);
    expectedAmoutn.put("DEPOSIT",367.5);

    when(transactionService.getTransaction(transactionList))
        .thenReturn(Mono.just(expectedAmoutn));

    Mono<ResponseEntity<Map<String, Double>>> response =
        transacctionController.getTransacion(transactionList);
    StepVerifier.create(response)
        .expectNextMatches( result -> {
          return expectedAmoutn.equals(result.getBody());
        })
        .verifyComplete();
  }

}