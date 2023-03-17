package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    void setFruit_ok() {
        fruitTransaction.setFruit(BANANA);
        Assertions.assertEquals(BANANA, fruitTransaction.getFruit());
    }

    @Test
    void setQuantity_ok() {
        fruitTransaction.setQuantity(QUANTITY2);
        Assertions.assertEquals(QUANTITY2, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_ok() {
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
    }

    @Test
    void getFruit_ok() {
        Assertions.assertEquals(APPLE, fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_ok() {
        Assertions.assertEquals(QUANTITY1, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_validCode_ok() {
        FruitTransaction.Operation operation =
                FruitTransaction.Operation.getOperation(BALANCE_OPERATION_CODE);
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void getOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getOperation(INVALID_OPERATION_CODE);
        });
    }

    @Test
    void equals_ok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        Assertions.assertEquals(fruitTransaction1, fruitTransaction2);
    }

    @Test
    void equals_notok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, QUANTITY1);
        Assertions.assertFalse(fruitTransaction1.equals(fruitTransaction2));
    }

    @Test
    void equals_fruit_notOk() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, QUANTITY1);
        Assertions.assertFalse(fruitTransaction1.equals(fruitTransaction2));
    }

    @Test
    void equals_quantity_notOk() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY2);
        assertNotEquals(fruitTransaction1, fruitTransaction2);
    }

    @Test
    void hashCode_sameObject_ok() {
        Assertions.assertEquals(fruitTransaction.hashCode(), fruitTransaction.hashCode());
    }

    @Test
    void hashCode_differentObjects_Ok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        Assertions.assertEquals(fruitTransaction1.hashCode(), fruitTransaction2.hashCode());
    }

    @Test
    void hashCode_differentObjects_notOk() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, QUANTITY1);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, QUANTITY2);
        assertNotEquals(fruitTransaction1.hashCode(), fruitTransaction2.hashCode());
    }
}
