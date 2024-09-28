package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    public void constructor_validParameters_createsFruitTransaction() {
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    public void getByCode_validCode_returnsCorrectOperation() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.getByCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getByCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.getByCode("r"));
    }

    @Test
    public void getByCode_invalidCode_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getByCode("invalid");
        });

        String expectedMessage = "Invalid operation code: invalid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

