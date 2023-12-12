package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static OperationHandler returnOperationHandler;
    private static Storage storage;

    @BeforeAll
    public static void init() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @BeforeEach
    public void setup() {
        storage = new StorageImpl();
        storage.put(BANANA, 234);
        storage.put(APPLE, 327);
    }

    @Test
    public void process_validData_ok() {
        final FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                APPLE,
                3);
        Storage expectedStorage = new StorageImpl();
        expectedStorage.put(BANANA, 234);
        expectedStorage.put(APPLE, 330);
        returnOperationHandler.process(fruitTransaction, storage);
        assertEquals(expectedStorage, storage);
    }
}
