package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Storage storage;
    private Map<FruitTransaction.Operation, OperationHandler> operations;
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        operations = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operations);
    }

    @Test
    public void operationStrategyTest() {
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(storage));

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        operationStrategy.process(transaction);

        assertEquals(10, storage.getAll().get("apple"));
    }

    @Test
    public void noOperationStrategyHandlerTest() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        assertThrows(RuntimeException.class,
                () -> operationStrategy.process(transaction));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
