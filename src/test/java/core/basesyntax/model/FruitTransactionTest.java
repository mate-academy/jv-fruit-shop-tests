package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fromCode_balance_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
    }

    @Test
    void fromCode_supply_ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
    }

    @Test
    void fromCode_purchase_ok() {
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
    }

    @Test
    void fromCode_return_ok() {
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalid_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void getCode_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
    }
}
