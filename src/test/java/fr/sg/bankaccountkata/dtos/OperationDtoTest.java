package fr.sg.bankaccountkata.dtos;

import fr.sg.bankaccountkata.entities.BankAccount;
import fr.sg.bankaccountkata.entities.Client;
import fr.sg.bankaccountkata.entities.Operation;
import fr.sg.bankaccountkata.enums.OperationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationDtoTest {
    private ModelMapper modelMapper = new ModelMapper();

    private static Operation operation;
    private static OperationDto operationDto;

    @BeforeAll
    public static void setUp() {
        Client client = new Client();
        client.setLastName("Dupont");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setClient(client);
        bankAccount.setBalance(1000d);

        operation = new Operation();
        operation.setBankAccount(bankAccount);
        operation.setType(OperationType.DEPOSIT);
        operation.setAmount(100d);

        operationDto = new OperationDto();
        operationDto.setBankAccountClientLastName("Dupont");
        operationDto.setBankAccountBalance(500d);
        operationDto.setType(OperationType.WITHDRAWAL);
        operationDto.setAmount(-100d);
        operationDto.setDate(LocalDateTime.now());
    }

    @Test
    public void mapOperationEntityToOperationDto() {
        OperationDto dto = modelMapper.map(operation, OperationDto.class);
        assertEquals(operation.getType(), dto.getType());
        assertEquals(operation.getAmount(), dto.getAmount());
        assertEquals(operation.getBankAccount().getBalance(), dto.getBankAccountBalance());
        assertEquals(operation.getBankAccount().getClient().getLastName(), dto.getBankAccountClientLastName());
    }

    @Test
    public void mapOperationDtoToOperationEntity() {
        Operation op = modelMapper.map(operationDto, Operation.class);
        assertEquals(op.getType(), operationDto.getType());
        assertEquals(op.getAmount(), operationDto.getAmount());
        assertEquals(op.getBankAccount().getBalance(), operationDto.getBankAccountBalance());
        assertEquals(op.getBankAccount().getClient().getLastName(), operationDto.getBankAccountClientLastName());
    }

}