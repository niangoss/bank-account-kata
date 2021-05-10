package fr.sg.bankaccountkata.repositories;

import fr.sg.bankaccountkata.entities.BankAccount;
import fr.sg.bankaccountkata.entities.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static Client client;
    private static BankAccount bankAccount;


    @BeforeEach
    void setUp() {
        client = new Client();
        client.setLastName("Dupont");

        bankAccount = new BankAccount();
        bankAccount.setBalance(990d);
    }

    @Test
    public void saveClientAndFindById() {
        Client savedClient = this.clientRepository.save(client);
        bankAccount.setClient(savedClient);
        BankAccount savedBankAccount = this.bankAccountRepository.save(bankAccount);
        assertEquals(savedBankAccount.getBalance(), 990d);
    }
}