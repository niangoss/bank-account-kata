package fr.sg.bankaccountkata.web.controllers;

import fr.sg.bankaccountkata.configuration.SwaggerConfig;
import fr.sg.bankaccountkata.dtos.OperationDto;
import fr.sg.bankaccountkata.dtos.OperationRequestBody;
import fr.sg.bankaccountkata.services.IOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api(tags = { SwaggerConfig.BANK_ACCOUNT })
@RestController
public class OperationController {

    @Autowired
    private IOperationService operationService;

    @ApiOperation(value = "See your bank account operations history!")
    @GetMapping(value="/operations")
    public List<OperationDto> getAllOperations() {
        return operationService.allOperations();
    }

    @ApiOperation(value = "Get your bank account balance!")
    @GetMapping(value="/balance")
    public Double getBalance() {
        return operationService.getBalance();
    }

    @ApiOperation(value = "Make a deposit in your account!")
    @PostMapping(value = "/deposit")
    public ResponseEntity<OperationDto> deposit(@RequestBody OperationRequestBody operation) {
        OperationDto newOperation = operationService.deposit(operation.getAmount());

        if(newOperation == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newOperation.getId())
                .toUri();
        return ResponseEntity.created(location).body(newOperation);
    }

    @ApiOperation(value = "Make a withdrawal from your account!")
    @PostMapping(value = "/withdraw")
    public ResponseEntity<OperationDto> withdraw(@RequestBody OperationRequestBody operation) {
        OperationDto newOperation = operationService.withdraw(operation.getAmount());

        if(newOperation == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newOperation.getId())
                .toUri();
        return ResponseEntity.created(location).body(newOperation);
    }

}