package fr.sg.bankaccountkata.services;

import fr.sg.bankaccountkata.dtos.OperationDto;

import java.util.List;


public interface IOperationService {

    OperationDto deposit(Double amount);

    OperationDto withdraw(Double amount);

    List<OperationDto> allOperations();

    Double getBalance();
}
