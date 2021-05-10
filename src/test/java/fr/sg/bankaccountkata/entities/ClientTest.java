package fr.sg.bankaccountkata.entities;

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
class ClientTest {

    @Autowired
    private TestEntityManager entityManager;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setLastName("Dupont");
    }

    @Test
    public void saveClient() {
        Client savedClient = this.entityManager.persistAndFlush(client);
        assertEquals(savedClient.getLastName(), "Dupont");
    }

    @Test
    public void createClientBlankNameException() throws Exception {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            this.entityManager.persist(new Client());
        });

        String expectedMessage = "Client name is mandatory";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}