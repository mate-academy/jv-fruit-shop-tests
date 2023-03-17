package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int QUANTITY1 = 100;
    private static final int QUANTITY2 = 200;
    private static final String INVALID_OPERATION_CODE = "strawberry";
    private static final String BALANCE_OPERATION_CODE = "b";

    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
    }

    @Test
    void setOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    void setFruit_ok() {
        fruitTransaction.setFruit(BANANA);
        assertEquals(BANANA, fruitTransaction.getFruit());
    }

    @Test
    void setQuantity_ok() {
        fruitTransaction.setQuantity(QUANTITY2);
        assertEquals(QUANTITY2, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
    }

    @Test
    void getFruit_ok() {
        assertEquals(APPLE, fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_ok() {
        assertEquals(QUANTITY1, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_validCode_ok() {
        FruitTransaction.Operation operation =
                FruitTransaction.Operation.getOperation(BALANCE_OPERATION_CODE);
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void getOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getOperation(INVALID_OPERATION_CODE);
        });
    }
}
