package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.ReturnImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReturnImplTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction zeroQuantityFruitTransaction;
    private static final Integer EXPECTED_QUANTITY = 70;
    private static final Integer INITIAL_QUANTITY = 50;
    private static final Integer ZERO_QUANTITY = 0;
    private static final String FRUIT = "apple";

    @BeforeAll
    public static void init() {
        operationHandler = new ReturnImpl();
        fruitTransaction = new FruitTransaction(Operation.RETURN, FRUIT, EXPECTED_QUANTITY);
        zeroQuantityFruitTransaction =
                new FruitTransaction(Operation.RETURN, FRUIT, ZERO_QUANTITY);
    }

    @BeforeEach
    public void clearFruitStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handleReturnTransaction_ValidTransaction_Ok() {
        operationHandler.operate(fruitTransaction);
        Assertions.assertEquals(EXPECTED_QUANTITY,
                FruitStorage.fruits.get(fruitTransaction.getFruit()),
                "Return transaction failed");
    }

    @Test
    public void handleReturnTransaction_QuantityUpdated_Ok() {
        FruitStorage.fruits.put(FRUIT, INITIAL_QUANTITY);
        operationHandler.operate(fruitTransaction);
        Assertions.assertEquals(INITIAL_QUANTITY + EXPECTED_QUANTITY,
                FruitStorage.fruits.get(FRUIT),
                "Return transaction with existing fruit failed");
    }

    @Test
    public void handleReturnTransaction_ZeroQuantity_Ok() {
        FruitStorage.fruits.put(FRUIT, INITIAL_QUANTITY);
        operationHandler.operate(zeroQuantityFruitTransaction);
        Assertions.assertEquals(INITIAL_QUANTITY, FruitStorage.fruits.get(FRUIT),
                "Return transaction with zero quantity failed");
    }

    @AfterAll
    public static void clear() {
        FruitStorage.fruits.clear();
    }

}
