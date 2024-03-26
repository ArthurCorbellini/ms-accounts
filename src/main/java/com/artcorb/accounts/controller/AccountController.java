package com.artcorb.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.artcorb.accounts.cfg.AccountsEnvironments;
import com.artcorb.accounts.constants.AccountConstants;
import com.artcorb.accounts.dto.CustomerDto;
import com.artcorb.accounts.dto.ResponseDto;
import com.artcorb.accounts.dto.ResponseErrorDto;
import com.artcorb.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(name = "CRUD REST API for Accounts",
    description = "CREATE, READ, UPDATE and DELETE accounts details")
@RestController
@Validated
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {

  @Autowired
  private IAccountService iAccountService;

  @Value("${build.version}")
  private String buildVersion;

  @Autowired
  private Environment environment;

  @Autowired
  private AccountsEnvironments environmentConfig;

  @Operation(summary = "Create Account REST API",
      description = "REST API to create new Customer and Account")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_201,
          description = AccountConstants.MESSAGE_201),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
    iAccountService.createAccount(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
  }

  @Operation(summary = "Read Account REST API",
      description = "REST API to read Customer and Account based on a mobile number")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(
      regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
    return ResponseEntity.status(HttpStatus.OK).body(iAccountService.fetchAccount(mobileNumber));
  }

  @Operation(summary = "Update Account REST API",
      description = "REST API to update Customer and Account based on a mobile number")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_417,
          description = AccountConstants.MESSAGE_417_UPDATE),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(
      @RequestBody @Valid CustomerDto customerDto) {
    if (iAccountService.updateAccount(customerDto)) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(summary = "Delete Account REST API",
      description = "REST API to delete Customer and Account based on a mobile number")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_417,
          description = AccountConstants.MESSAGE_417_DELETE),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(
      regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
    if (iAccountService.deleteAccount(mobileNumber)) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(summary = "Get build information",
      description = "Get build information that is deployed into accounts microservice")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @Operation(summary = "Get Java version", description = "Get Java version of enviroment")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @Operation(summary = "Get Contact Info",
      description = "Contact Info details that can be reached out in case of any issues")
  @ApiResponses({
      @ApiResponse(responseCode = AccountConstants.STATUS_200,
          description = AccountConstants.MESSAGE_200),
      @ApiResponse(responseCode = AccountConstants.STATUS_500,
          description = AccountConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/contact-info")
  public ResponseEntity<AccountsEnvironments> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(environmentConfig);
  }
}
