package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String FRUIT = "apple";
    private StorageService storageService;
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        supplyOperationHandler = new SupplyOperationHandler(storageService);
    }

    @Test
    public void handle_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT);
        transaction.setAmount(5);

        Map<String, Integer> storage = new HashMap<>();

        supplyOperationHandler.handle(transaction, storage);
        assertEquals(5, storageService.getStorage().get(FRUIT).intValue());
    }

    @Test
    public void handle_negativeAmount_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT);
        transaction.setAmount(-3);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supplyOperationHandler
                        .handle(transaction, storageService.getStorage()));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
}
