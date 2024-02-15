package com.corbellini.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Response", description = "Schema to hold successful response information")
@Data
@AllArgsConstructor
public class ResponseDto {

  @Schema(description = "Response status code", example = "200")
  private String statusCode;

  @Schema(description = "Response status message", example = "Request processed successfully")
  private String statusMessage;
}
