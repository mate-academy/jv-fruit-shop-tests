package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.PurchaseImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PurchaseImplTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction invalidFruitTransaction;
    private static final Integer EXPECTED_QUANTITY = 70;
    private static final Integer INITIAL_QUANTITY = 100;
    private static final String FRUIT = "apple";

    @BeforeAll
    public static void initClass() {
        operationHandler = new PurchaseImpl();
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, FRUIT, EXPECTED_QUANTITY);
        invalidFruitTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", EXPECTED_QUANTITY);
    }

    @BeforeEach
    public void clearFruitStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handlePurchaseTransaction_ValidTransaction_Ok() {
        FruitStorage.fruits.put(FRUIT, INITIAL_QUANTITY);
        operationHandler.operate(fruitTransaction);
        Assertions.assertEquals(INITIAL_QUANTITY - EXPECTED_QUANTITY,
                FruitStorage.fruits.get(fruitTransaction.getFruit()),
                "Purchase transaction failed");
    }

    @Test
    public void handlePurchaseTransaction_InvalidTransaction_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                operationHandler.operate(invalidFruitTransaction),
                "RuntimeException expected");
    }

    @AfterAll
    public static void clear() {
        FruitStorage.fruits.clear();
    }

}
