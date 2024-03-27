package core.basesyntax.service.impl.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operations.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final String FRUIT_NAME = "Apple";
    private static final int INITIAL_QUANTITY = 10;
    private static final int PURCHASE_QUANTITY = 5;

    private PurchaseOperationHandler purchaseOperationHandler;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        purchaseOperationHandler = new PurchaseOperationHandler(storage);
    }

    @Test
    public void apply_SupplyFruit_ok() {
        storage.addFruit(new Fruit(FRUIT_NAME), INITIAL_QUANTITY);
        FruitTransactionDto dto = new FruitTransactionDto(Operation.PURCHASE, FRUIT_NAME,
                PURCHASE_QUANTITY);
        purchaseOperationHandler.apply(dto);
        Map<Fruit, Integer> fruits = storage.getFruits();
        assertEquals(INITIAL_QUANTITY - PURCHASE_QUANTITY,
                fruits.getOrDefault(new Fruit(FRUIT_NAME), 0));
    }

    @Test
    public void apply_PurchaseMoreThanAvailable_notOk() {
        storage.addFruit(new Fruit(FRUIT_NAME), INITIAL_QUANTITY);
        FruitTransactionDto dto = new FruitTransactionDto(Operation.PURCHASE, FRUIT_NAME,
                INITIAL_QUANTITY + 1);
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.apply(dto));
    }

    @AfterEach
    public void tearDown() {
        storage.getFruits().clear();
    }
}
