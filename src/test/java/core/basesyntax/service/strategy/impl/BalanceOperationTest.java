package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String NEGATIVE_VALUE_MESSAGE
            = "Unsupported value for BALANCE operation: -13";
    private StorageRepository repository = new StorageRepository();
    private BalanceOperation balanceOperation = new BalanceOperation(repository);
    private Transaction twentyValueTransaction;
    private Transaction negativeValueTransaction;

    @BeforeEach
    void setUp() {
        repository.getProducts().clear();
        twentyValueTransaction = new Transaction(Operation.BALANCE, "banana", 20);
        negativeValueTransaction = new Transaction(Operation.BALANCE, "banana", -13);
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
