package com.corbellini.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Customer", description = "Schema to hold Customer and Account information")
@Data
public class CustomerDto {

  @Schema(description = "Customer name", example = "Example Name")
  @NotEmpty(message = "Name can not be a null or empty")
  @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
  private String name;

  @Schema(description = "Customer e-mail address", example = "example@example.com")
  @NotEmpty(message = "E-mail address can not be a null or empty")
  @Email(message = "E-mail address should be a valid value")
  private String email;

  @Schema(description = "Customer mobile number", example = "1234567890")
  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
  private String mobileNumber;

  @Schema(description = "Account details of the Customer")
  @Valid
  private AccountDto accountDto;
}
