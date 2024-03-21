package core.basesyntax.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageRepositoryTest {
    private StorageRepository repository;

    @BeforeEach
    void setUp() {
        repository = new StorageRepository();
    }

    @AfterEach
    void cleanUp() {
        repository.getProducts().clear();
    }

    @Test
    void addProduct_validProduct_ok() {
        Transaction transaction = new Transaction(Operation.BALANCE, "banana", 23);
        repository.add(transaction);
        assertEquals(1, repository.getProducts().size());
        assertTrue(repository.getProducts().keySet().contains(transaction.getProduct()));
    }
}
