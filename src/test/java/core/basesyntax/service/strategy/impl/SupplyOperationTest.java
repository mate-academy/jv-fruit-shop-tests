package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String NEGATIVE_VALUE_MESSAGE
            = "Unsupported value for SUPPLY operation: -103";
    private StorageRepository repository = new StorageRepository();
    private SupplyOperation supplyOperation = new SupplyOperation(repository);
    private Transaction twentyValueTransaction;
    private Transaction negativeValueTransaction;

    @BeforeEach
    void setUp() {
        repository.getProducts().clear();
        twentyValueTransaction = new Transaction(Operation.SUPPLY, "banana", 20);
        negativeValueTransaction = new Transaction(Operation.SUPPLY, "banana", -123);

        repository.add(twentyValueTransaction);
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
