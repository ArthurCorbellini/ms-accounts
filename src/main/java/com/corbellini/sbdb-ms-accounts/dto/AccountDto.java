package com.corbellini.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(name = "Account", description = "Schema to hold Account information")
@Data
public class AccountDto {

  @Schema(description = "Account number", example = "5387459238")
  @NotNull(message = "Account number can not be a null or empty")
  private Long accountNumber;

  @Schema(description = "Account type", example = "Example Type")
  @NotEmpty(message = "Account type can not be a null or empty")
  private String accountType;

  @Schema(description = "Account branch address", example = "Rua Brasil, 123")
  @NotEmpty(message = "Branch address can not be a null or empty")
  private String branchAddress;
}
