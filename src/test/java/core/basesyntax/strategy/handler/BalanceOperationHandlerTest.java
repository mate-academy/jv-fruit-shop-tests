package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.constants.Constants;
import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;
    private static Storage storage;

    @BeforeAll
    public static void init() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    public void setup() {
        storage = new StorageImpl();
        storage.put(Constants.BANANA.getValue(), 234);
        storage.put(Constants.APPLE.getValue(), 327);
    }

    @Test
    public void process_validData_ok() {
        final FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.ORANGE.getValue(),
                888);
        Storage expectedStorage = new StorageImpl();
        expectedStorage.put(Constants.BANANA.getValue(), 234);
        expectedStorage.put(Constants.APPLE.getValue(), 327);
        expectedStorage.put(Constants.ORANGE.getValue(), 888);
        balanceOperationHandler.process(fruitTransaction, storage);
        assertEquals(expectedStorage, storage);
    }
}
