package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String VALID_FRUIT = "apple";
    private static final Integer VALID_QUANTITY = 20;

    @Test
    void fruitTransaction_validData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,VALID_FRUIT,VALID_QUANTITY);
        assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
        assertEquals(VALID_FRUIT, fruitTransaction.getFruit());
        assertEquals(VALID_QUANTITY, fruitTransaction.getQuantity());

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                VALID_FRUIT, VALID_QUANTITY);
        assertEquals(FruitTransaction.Operation.PURCHASE,
                fruitTransaction.getOperation());

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                VALID_FRUIT, VALID_QUANTITY);
        assertEquals(FruitTransaction.Operation.RETURN,
                fruitTransaction.getOperation());

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                VALID_FRUIT, VALID_QUANTITY);
        assertEquals(FruitTransaction.Operation.SUPPLY,
                fruitTransaction.getOperation());
    }

    @Test
    void getByCode_operationCode_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getByCode("b"));

        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode("p"));

        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getByCode("r"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getByCode("s"));
    }

    @Test
    void getByCode_notValidOperation_notOk() {
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getByCode("a"));
    }
}
