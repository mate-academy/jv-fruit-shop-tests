package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String NEGATIVE_VALUE_MESSAGE
            = "Unsupported value for BALANCE operation: -13";
    private StorageRepository repository = new StorageRepository();
    private BalanceOperation balanceOperation = new BalanceOperation(repository);
    private Transaction tenValueTransaction;
    private Transaction twentyValueTransaction;
    private Transaction negativeValueTransaction;

    @BeforeEach
    void setUp() {
        tenValueTransaction = new Transaction(Operation.BALANCE, "apple", 10);
        twentyValueTransaction = new Transaction(Operation.BALANCE, "banana", 20);
        negativeValueTransaction = new Transaction(Operation.BALANCE, "banana", -13);
    }

    @AfterEach
    void clearUp() {
        repository.getProducts().clear();
    }

    @Test
    void balanceOperation_addTenValue_tenShouldPresentInRepository() {
        balanceOperation.execute(tenValueTransaction);
        assertEquals(tenValueTransaction.getValue(), repository.getProducts().get("apple"));
    }

    @Test
    void balanceOperation_addTwentyValue_twentyShouldPresentInRepository() {
        balanceOperation.execute(twentyValueTransaction);
        assertEquals(twentyValueTransaction.getValue(), repository.getProducts().get("banana"));
    }

    @Test
    void balanceOperation_addNegativeValue_throwRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            balanceOperation.execute(negativeValueTransaction);
        });
        assertEquals(NEGATIVE_VALUE_MESSAGE, exception.getMessage());
    }
}
