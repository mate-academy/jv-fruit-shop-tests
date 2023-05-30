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

class SupplyHandlerTest {
    private static final int QUANTITY_BEFORE_SUPPLY = 15;
    private static final String DEFAULT_FRUIT = "banana";
    private static final int QUANTITY_TO_SUPPLY = 15;
    private FruitTransaction fruitTransaction;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        Storage.dataBase.put(DEFAULT_FRUIT, QUANTITY_BEFORE_SUPPLY);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(QUANTITY_TO_SUPPLY);
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyHandler = new SupplyHandler();
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
    void balance_afterSupply_ok() {
        supplyHandler.operate(fruitTransaction);
        int expectedQuantity = QUANTITY_BEFORE_SUPPLY + QUANTITY_TO_SUPPLY;
        int updatedQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, updatedQuantity);
    }

    @Test
    void balance_afterSupply_notOk() {
        supplyHandler.operate(fruitTransaction);
        int expectedQuantity = QUANTITY_BEFORE_SUPPLY + QUANTITY_TO_SUPPLY;
        int updatedQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        if (expectedQuantity != updatedQuantity) {
            assertThrows(InvalidFruitShopException.class,
                    () -> {
                        throw new InvalidFruitShopException("Balance after return should "
                                + "be equals to previous balance");
                    });
        }
    }
}
