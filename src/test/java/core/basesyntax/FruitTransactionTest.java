package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    public void transactionIncorrectOperation_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.valueOf("v"), "fruit", 4));
    }

    @Test
    public void transactionValidArguments_noneThrowsException() {
        assertDoesNotThrow(() ->
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "fruit", 3));
    }

    @Test
    public void transactionNullFruitAndOperation_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, 3));
        assertThrows(IllegalArgumentException.class, () -> new FruitTransaction(null, "fruit", 3));
    }

    @Test
    public void transactionNegativeQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "fruit", -1));
    }
}
