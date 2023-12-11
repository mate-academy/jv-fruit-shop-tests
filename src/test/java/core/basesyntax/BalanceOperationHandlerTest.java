package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BalanceOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static OperationHandler balanceOperationHandler;
    private static Storage storage;

    @BeforeAll
    public static void setBalanceOperationHandler() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    public void setStorage() {
        storage = new StorageImpl();
        storage.put(BANANA, 234);
        storage.put(APPLE, 327);
    }

    @Test
    public void process_validData_Ok() {
        final FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                ORANGE,
                888);
        Storage expectedStorage = new StorageImpl();
        expectedStorage.put(BANANA, 234);
        expectedStorage.put(APPLE, 327);
        expectedStorage.put(ORANGE, 888);
        balanceOperationHandler.process(fruitTransaction, storage);
        assertEquals(expectedStorage, storage);
    }
}
