package com.example.transaction.demo.transaction.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Model representing a transaction")
public class Transaction {
  @Schema(description = "Unique identifier of  transaction", example = "1")
  private String id;
  @Schema(description = "Transaction type", example = "DEPOSIT|WITHDRAW")
  private String type;
  @Schema(description = "Amount of transaction", example = "150.0")
  private Double amount;
}
