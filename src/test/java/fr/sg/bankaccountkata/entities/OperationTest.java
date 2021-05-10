package fr.sg.bankaccountkata.entities;

import fr.sg.bankaccountkata.enums.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class OperationTest {

    @Autowired
    private TestEntityManager entityManager;

    private BankAccount bankAccount;
    private Client client;
    private Operation operation;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setLastName("Dupont");

        bankAccount = new BankAccount();

        operation = new Operation(OperationType.DEPOSIT, 100d);
    }

    @Test
    public void saveOperation() {
        Client savedClient = this.entityManager.persistAndFlush(client);
        bankAccount.setClient(savedClient);
        BankAccount savedBankAccount = this.entityManager.persistAndFlush(bankAccount);
        assertEquals(savedBankAccount.getBalance(), 0d);

        operation.setBankAccount(savedBankAccount);
        Operation savedOperation = this.entityManager.persistAndFlush(operation);
        assertEquals(savedOperation.getType(), OperationType.DEPOSIT);
        assertEquals(savedOperation.getAmount(), 100d);
    }

    @Test
    public void createBankAccountNullException() throws Exception {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            this.entityManager.persist(new Operation(OperationType.WITHDRAWAL, -10d));
        });

        String expectedMessage = "Bank account is mandatory";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createOperationTypeNullException() throws Exception {
        Client savedClient = this.entityManager.persistAndFlush(client);
        bankAccount.setClient(savedClient);
        BankAccount savedBankAccount = this.entityManager.persistAndFlush(bankAccount);
        assertEquals(savedBankAccount.getBalance(), 0d);

        Operation nullTypeOperation = new Operation();
        nullTypeOperation.setBankAccount(savedBankAccount);
        nullTypeOperation.setAmount(100d);

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            this.entityManager.persist(nullTypeOperation);
        });

        String expectedMessage = "Operation type is mandatory";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createAmountNullException() throws Exception {
        Client savedClient = this.entityManager.persistAndFlush(client);
        bankAccount.setClient(savedClient);
        BankAccount savedBankAccount = this.entityManager.persistAndFlush(bankAccount);
        assertEquals(savedBankAccount.getBalance(), 0d);

        Operation nullAmountOperation = new Operation();
        nullAmountOperation.setBankAccount(savedBankAccount);
        nullAmountOperation.setType(OperationType.WITHDRAWAL);

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            this.entityManager.persist(nullAmountOperation);
        });

        String expectedMessage = "Amount is mandatory";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}