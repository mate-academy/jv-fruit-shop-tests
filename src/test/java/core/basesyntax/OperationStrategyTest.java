package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.serviceimpl.OperationStrategyImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.utility.FruitTransactionValidator;
import core.basesyntax.utility.FruitTransactionValidatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategy operationStrategy;
    private Storage storage;
    private FruitTransaction fruitTransaction;
    private FruitTransactionValidator validator;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        validator = new FruitTransactionValidatorImpl();
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler(validator));
        operationStrategy = new OperationStrategyImpl(handlers);
        storage = new InMemoryStorage();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void successOperationReturn_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        storage.putQuantity("banana", 30);
        operationStrategy.handle(fruitTransaction, storage);
        Assertions.assertEquals(50, storage.getQuantity("banana"));
    }

    @Test
    void operationBalanceWithNullStorage_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.handle(fruitTransaction, null));
        Assertions.assertTrue(ex.getMessage().contains("can't be null"));
    }

    @Test
    void operationSupplyWithNullFruitTransaction_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.handle(null, storage));
        Assertions.assertTrue(ex.getMessage().contains("can't be null"));
    }

    @Test
    void withNullOperation_notOk() {
        fruitTransaction.setOperation(null);
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.handle(fruitTransaction, storage));
        Assertions.assertTrue(ex.getMessage().contains("No handler for operation"));
    }
}
