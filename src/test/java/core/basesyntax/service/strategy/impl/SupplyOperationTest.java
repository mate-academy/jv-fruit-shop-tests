package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String NEGATIVE_VALUE_MESSAGE
            = "Unsupported value for SUPPLY operation: -103";
    private StorageRepository repository = new StorageRepository();
    private SupplyOperation supplyOperation = new SupplyOperation(repository);
    private Transaction tenValueTransaction;
    private Transaction twentyValueTransaction;
    private Transaction negativeValueTransaction;

    @BeforeEach
    void setUp() {
        tenValueTransaction = new Transaction(Operation.SUPPLY, "apple", 10);
        twentyValueTransaction = new Transaction(Operation.SUPPLY, "banana", 20);
        negativeValueTransaction = new Transaction(Operation.SUPPLY, "banana", -123);

        repository.add(tenValueTransaction);
        repository.add(twentyValueTransaction);
    }

    @AfterEach
    void clearUp() {
        repository.getProducts().clear();
    }

    @Test
    void balanceOperation_addTenValue_tenShouldPresentInRepository() {
        supplyOperation.execute(tenValueTransaction);
        assertEquals(tenValueTransaction.getValue(), repository.getProducts().get("apple"));
    }

    @Test
    void balanceOperation_addTwentyValue_twentyShouldPresentInRepository() {
        supplyOperation.execute(twentyValueTransaction);
        assertEquals(twentyValueTransaction.getValue(), repository.getProducts().get("banana"));
    }

    @Test
    void balanceOperation_addNegativeValue_throwRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyOperation.execute(negativeValueTransaction);
        });
        assertEquals(NEGATIVE_VALUE_MESSAGE, exception.getMessage());
    }
}
