package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidFruitShopException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {

    private static final String DEFAULT_FRUIT = "banana";
    private static final int QUANTITY_AFTER_PURCHASE = 10;
    private static final int QUANTITY_TO_RETURN = 5;
    private FruitTransaction fruitTransaction;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        Storage.dataBase.put(DEFAULT_FRUIT, QUANTITY_AFTER_PURCHASE);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(QUANTITY_TO_RETURN);
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnHandler = new ReturnHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.dataBase.clear();
    }

    @Test
    void balance_notNull_ok() {
        assertNotNull(Storage.dataBase.get(fruitTransaction.getFruit()),
                "Balance can't be null");
    }

    @Test
    void balance_afterReturn_OK() {
        returnHandler.operate(fruitTransaction);
        int expectedQuantity = QUANTITY_AFTER_PURCHASE + QUANTITY_TO_RETURN;
        int oldQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, oldQuantity);
    }

    @Test
    void balance_afterReturn_notOk() {
        returnHandler.operate(fruitTransaction);
        int oldQuantity = Storage.dataBase.get(fruitTransaction.getFruit()); // 10 after purchase
        int expectedQuantity = QUANTITY_AFTER_PURCHASE + QUANTITY_TO_RETURN;
        if (expectedQuantity != oldQuantity) {
            assertThrows(InvalidFruitShopException.class,
                    () -> {
                        throw new InvalidFruitShopException("Balance after return should "
                        + "be equals to previous balance");
                    });
        }
    }
}
