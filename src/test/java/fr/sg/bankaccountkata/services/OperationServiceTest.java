package fr.sg.bankaccountkata.services;

import fr.sg.bankaccountkata.dtos.OperationDto;
import fr.sg.bankaccountkata.entities.BankAccount;
import fr.sg.bankaccountkata.entities.Operation;
import fr.sg.bankaccountkata.repositories.BankAccountRepository;
import fr.sg.bankaccountkata.repositories.OperationRepository;
import fr.sg.bankaccountkata.services.impl.OperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class OperationServiceTest {

    @Autowired
    OperationService operationService;

    @MockBean
    OperationRepository operationRepository;

    @MockBean
    BankAccountRepository bankAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(1000d);

        Operation operation = new Operation();
        operation.setBankAccount(bankAccount);

        when(bankAccountRepository.findById(anyInt()))
                .thenReturn(bankAccount);

        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenReturn(bankAccount);

        when(operationRepository.save(any(Operation.class)))
                .thenReturn(operation);

    }

    @Test
    void deposit() {
        OperationDto deposit = operationService.deposit(100d);
        assertNotNull(deposit);
        assertEquals(deposit.getBankAccountBalance(), 1100d);
    }

    @Test
    void withdraw() {
        OperationDto withdraw = operationService.withdraw(100d);
        assertNotNull(withdraw);
        assertEquals(withdraw.getBankAccountBalance(), 900d);
    }
}