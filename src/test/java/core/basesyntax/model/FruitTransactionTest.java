package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    public void getByCode_validCode_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.getByCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.getByCode("s"));
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
