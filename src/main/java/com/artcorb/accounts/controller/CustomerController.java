package com.artcorb.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.artcorb.accounts.constants.AccountConstants;
import com.artcorb.accounts.dto.CustomerDetailsDto;
import com.artcorb.accounts.dto.ResponseErrorDto;
import com.artcorb.accounts.service.ICustomerService;
import com.artcorb.accounts.util.FilterUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@Tag(name = "REST API for Customers", description = "Rest API to FETCH customer details")
@RestController
@Validated
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

  private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private ICustomerService iCustomerService;

  @Operation(summary = "Read Customer Details REST API",
      description = "REST API to read Customer Details based on a mobile number")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/fetchCustomerDetails")
  public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
      @RequestHeader(FilterUtil.CORRELATION_ID) String correlationId,
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
          message = "Mobile number must be 10 digits") String mobileNumber) {

    logger.debug(FilterUtil.CORRELATION_ID + " found: {} ", correlationId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(iCustomerService.fetchCustomerDetails(correlationId, mobileNumber));
  }

}
