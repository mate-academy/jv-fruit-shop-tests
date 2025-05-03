package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.getOperationFromCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";

    @Test
    public void get_operation_from_code() {
        assertEquals(FruitTransaction.Operation.BALANCE, getOperationFromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, getOperationFromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, getOperationFromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, getOperationFromCode("r"));
    }

    @Test
    public void set_operation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, 10);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    public void set_fruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, 10);
        transaction.setFruit(BANANA);
        assertEquals(BANANA, transaction.getFruit());
    }

    @Test
    public void set_quantity() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, 10);
        transaction.setQuantity(100);
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void getOperationFromCode_unknownCode() {
        Assertions.assertThrows(RuntimeException.class,
                () -> FruitTransaction.getOperationFromCode("x"));
    }
}
