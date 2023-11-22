package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoCsvImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final int POSITIVE_QUANTITY = 13;
    private static final int ZERO_QUANTITY = 0;
    private static FruitShopDao fruitShopDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction validTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitShopDao = new FruitShopDaoCsvImpl();
        operationHandler = new SupplyOperationHandler(fruitShopDao);
        validTransaction = new FruitTransaction(Operation.SUPPLY, FRUIT, POSITIVE_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitQuantities.clear();
    }

    @Test
    void handleOperation_positiveQuantity_ok() {
        Storage.fruitQuantities.put(FRUIT, POSITIVE_QUANTITY);
        Integer initialQuantity = Storage.fruitQuantities.get(FRUIT);
        operationHandler.handleOperation(validTransaction);

        Integer expected = initialQuantity + POSITIVE_QUANTITY;
        Integer actual = Storage.fruitQuantities.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void handleOperation_zeroQuantity_ok() {
        validTransaction.setQuantity(ZERO_QUANTITY);
        Storage.fruitQuantities.put(FRUIT, POSITIVE_QUANTITY);
        operationHandler.handleOperation(validTransaction);

        Integer expected = POSITIVE_QUANTITY;
        Integer actual = Storage.fruitQuantities.get(FRUIT);
        assertEquals(expected, actual);
    }
}
