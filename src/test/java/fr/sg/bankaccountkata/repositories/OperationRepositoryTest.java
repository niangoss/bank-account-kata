package fr.sg.bankaccountkata.repositories;

import fr.sg.bankaccountkata.entities.BankAccount;
import fr.sg.bankaccountkata.entities.Client;
import fr.sg.bankaccountkata.entities.Operation;
import fr.sg.bankaccountkata.enums.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class OperationRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private OperationRepository operationRepository;

    private static Client client;
    private static BankAccount bankAccount;
    private static Operation operation;


    @BeforeEach
    void setUp() {
        client = new Client();
        client.setLastName("Dupont");

        bankAccount = new BankAccount();
        bankAccount.setBalance(990d);

        operation = new Operation(OperationType.DEPOSIT, 100d);
    }

    @Test
    public void saveClientAndFindById() {
        Client savedClient = this.clientRepository.save(client);
        bankAccount.setClient(savedClient);
        BankAccount savedBankAccount = this.bankAccountRepository.save(bankAccount);
        assertEquals(savedBankAccount.getBalance(), 990d);

        operation.setBankAccount(savedBankAccount);
        Operation savedOperation = this.operationRepository.save(operation);
        assertEquals(savedOperation.getType(), OperationType.DEPOSIT);
        assertEquals(savedOperation.getAmount(), 100d);
    }
}