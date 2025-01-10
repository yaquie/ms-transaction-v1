package com.example.transaction.demo.transaction.contoller;

import com.example.transaction.demo.transaction.model.Transaction;
import com.example.transaction.demo.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction Controller", description = "APIs for Transaction Management")
public class TransacctionController {

  @Autowired
  private TransactionService transactionService;

  @GetMapping("/validate")
  @Operation(
      summary = "Validate Transactions",
      description = "Validates a list of transactions and returns the result.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Successful response with transaction details",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Map.class),
                  examples = {
                      @ExampleObject(
                          name = "Valid Transactions",
                          summary = "A valid example of transactions",
                          description = "Example request body with valid transactions",
                          value = "[{\"id\": \"1\", \"amount\": 150, \"type\": \"DEPOSIT\"}, {\"id\": \"2\", \"amount\": 50, \"type\": \"WITHDRAW\"}]"
                      )
                  }
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Invalid request body format",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Map.class)
              )
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error"
          )
      }
  )
  public Mono<ResponseEntity<Map<String, Double>>>
  getTransacion(@RequestBody List<Transaction> transactions){
    return transactionService.getTransaction(transactions)
        .map(response -> {
         return  ResponseEntity.ok()
              .body(response);
        }).onErrorResume((Throwable throwable) -> handleException(throwable));
  }

  private Mono<ResponseEntity<Map<String, Double>>> handleException(Throwable throwable){
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public Mono<ResponseEntity<Map<String, String>>>
  badRequestException(HttpMessageNotReadableException e) {
    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", "Invalid request body format: " + e.getMessage())));
  }
}
