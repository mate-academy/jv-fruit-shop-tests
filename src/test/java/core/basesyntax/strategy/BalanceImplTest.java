package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.BalanceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BalanceImplTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static final Integer EXPECTED_QUANTITY = 70;
    private static final Integer INITIAL_QUANTITY = 50;
    private static final String FRUIT = "apple";

    @BeforeAll
    public static void init() {
        operationHandler = new BalanceImpl();
        fruitTransaction = new FruitTransaction(Operation.BALANCE, FRUIT, EXPECTED_QUANTITY);
    }

    @BeforeEach
    public void clearFruitStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handle_ValidBalanceTransaction_isOk() {
        operationHandler.operate(fruitTransaction);
        Assertions.assertEquals(EXPECTED_QUANTITY,
                FruitStorage.fruits.get(fruitTransaction.getFruit()),
                "Balance transaction failed");
    }

    @Test
    public void handle_QuantityUpdated_isOk() {
        FruitStorage.fruits.put(FRUIT, INITIAL_QUANTITY);
        operationHandler.operate(fruitTransaction);
        Assertions.assertEquals(EXPECTED_QUANTITY, FruitStorage.fruits.get(FRUIT),
                "Balance transaction with existing fruit quantity failed");
    }

    @AfterAll
    public static void clear() {
        FruitStorage.fruits.clear();
    }
}
