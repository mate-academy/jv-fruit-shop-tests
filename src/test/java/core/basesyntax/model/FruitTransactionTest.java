package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String VALID_FRUIT_BANANA = "banana";
    private static final String VALID_FRUIT_APPLE = "apple";
    private static final String INVALID_FRUIT_ORANGE = "orange";
    private static final int FIFTY_VALUE = 50;
    private static final int ZERO_VALUE = 0;
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    private static final FruitTransaction VALID_FRUIT_T_BALANCE =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana", 100);
    private static final FruitTransaction VALID_FRUIT_T_SUPPLY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "apple", 20);
    private static final FruitTransaction VALID_FRUIT_T_SUPPLY_2 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "apple", 20);
    private static final FruitTransaction FIFTY_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana", FIFTY_VALUE);
    private static final FruitTransaction ZERO_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "apple", ZERO_VALUE);
    private static final FruitTransaction MAX_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "apple", MAX_VALUE);

    @Test
    void testEquals() {
        assertEquals(VALID_FRUIT_T_SUPPLY, VALID_FRUIT_T_SUPPLY_2);
        assertNotEquals(VALID_FRUIT_T_SUPPLY, VALID_FRUIT_T_BALANCE);
        assertNotEquals(VALID_FRUIT_T_SUPPLY_2, VALID_FRUIT_T_BALANCE);
    }

    @Test
    void testHashCode() {
        assertEquals(VALID_FRUIT_T_SUPPLY.hashCode(), VALID_FRUIT_T_SUPPLY_2.hashCode());
        assertNotEquals(VALID_FRUIT_T_SUPPLY.hashCode(), VALID_FRUIT_T_BALANCE.hashCode());
        assertNotEquals(VALID_FRUIT_T_SUPPLY_2.hashCode(), VALID_FRUIT_T_BALANCE.hashCode());
    }

    @Test
    void getOperation() {
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation supply = FruitTransaction.Operation.SUPPLY;
        assertEquals(balance, VALID_FRUIT_T_BALANCE.getOperation());
        assertEquals(supply, VALID_FRUIT_T_SUPPLY.getOperation());
        assertNotEquals(supply, VALID_FRUIT_T_BALANCE.getOperation());
    }

    @Test
    void getFruit() {
        assertEquals(VALID_FRUIT_BANANA, VALID_FRUIT_T_BALANCE.getFruit());
        assertEquals(VALID_FRUIT_APPLE, VALID_FRUIT_T_SUPPLY.getFruit());
        assertNotEquals(INVALID_FRUIT_ORANGE, VALID_FRUIT_T_BALANCE.getFruit());
    }

    @Test
    void getQuantity() {
        assertEquals(FIFTY_VALUE, FIFTY_QUANTITY.getQuantity());
        assertEquals(ZERO_VALUE, ZERO_QUANTITY.getQuantity());
        assertEquals(MAX_VALUE, MAX_QUANTITY.getQuantity());
    }
}
