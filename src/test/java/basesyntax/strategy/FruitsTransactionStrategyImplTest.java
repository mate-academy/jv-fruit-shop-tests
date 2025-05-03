package basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.FruitTransactionServiceImpl;
import basesyntax.db.Storage;
import basesyntax.exceptions.TransactionException;
import basesyntax.model.Fruit;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsTransactionStrategyImplTest {
    private static final Map<String, FruitHandler> fruitHandlerMap = Map.of(
            FruitTransactionServiceImpl.Operation.BALANCE.getCode(), new FruitBalance(),
            FruitTransactionServiceImpl.Operation.PURCHASE.getCode(), new FruitPurchase(),
            FruitTransactionServiceImpl.Operation.SUPPLY.getCode(), new FruitSupply(),
            FruitTransactionServiceImpl.Operation.RETURN.getCode(), new FruitReturn()
    );
    private static final FruitsTransactionStrategy fruitsTransactionStrategy =
            new FruitsTransactionStrategyImpl(fruitHandlerMap);
    private static final Map<String, Fruit> fruitsData = Storage.getFruits();
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_BALANCE_QUANTITY = 40;
    private static final int FRUIT_SUPPLY_QUANTITY = 10;
    private static final int FRUIT_PURCHASE_QUANTITY = 30;
    private static final int FRUIT_RETURN_QUANTITY = 10;

    @BeforeEach
    void setUp() {
        fruitsData.put(FRUIT_NAME, new Fruit(FRUIT_NAME, FRUIT_BALANCE_QUANTITY));
    }

    @AfterAll
    static void afterAll() {
        fruitsData.clear();
    }

    @Test
    void fruitHandler_nullType_Ok() {
        assertThrows(TransactionException.class, () -> {
            fruitsTransactionStrategy.fruitHandler(null);
        });
    }

    @Test
    void fruitHandlerBalance_Ok() {
        FruitHandler fruitHandler = fruitsTransactionStrategy.fruitHandler(
                FruitTransactionServiceImpl.Operation.BALANCE.getCode()
        );
        fruitHandler.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_BALANCE_QUANTITY);
        assertEquals(fruitsData.get(FRUIT_NAME).getQuantity(), FRUIT_BALANCE_QUANTITY);
    }

    @Test
    void fruitHandlerSupply_Ok() {
        FruitHandler fruitHandler = fruitsTransactionStrategy.fruitHandler(
                FruitTransactionServiceImpl.Operation.SUPPLY.getCode()
        );
        fruitHandler.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_SUPPLY_QUANTITY);
        assertEquals(
                fruitsData.get(FRUIT_NAME).getQuantity(),
                FRUIT_BALANCE_QUANTITY + FRUIT_SUPPLY_QUANTITY
        );
    }

    @Test
    void fruitHandlerPurchase_Ok() {
        FruitHandler fruitHandler = fruitsTransactionStrategy.fruitHandler(
                FruitTransactionServiceImpl.Operation.PURCHASE.getCode()
        );
        fruitHandler.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_PURCHASE_QUANTITY);
        assertEquals(
                fruitsData.get(FRUIT_NAME).getQuantity(),
                FRUIT_BALANCE_QUANTITY - FRUIT_PURCHASE_QUANTITY
        );
    }

    @Test
    void fruitHandlerReturn_Ok() {
        FruitHandler fruitHandler = fruitsTransactionStrategy.fruitHandler(
                FruitTransactionServiceImpl.Operation.RETURN.getCode()
        );
        fruitHandler.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_RETURN_QUANTITY);
        assertEquals(
                fruitsData.get(FRUIT_NAME).getQuantity(),
                FRUIT_BALANCE_QUANTITY + FRUIT_RETURN_QUANTITY
        );
    }
}
