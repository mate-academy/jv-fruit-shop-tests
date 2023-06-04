package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;
//
public class FruitTransactionTest {

    @Test
    public void add_fruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(OperationType.BALANCE,
                "banana", 10);
        OperationType expectedOperationType = OperationType.BALANCE;
        String expectedName = "banana";
        int expectedQuantity = 10;

        Assert.assertEquals("Expected Operation Type: " + expectedOperationType
                + " but was " + fruitTransaction.getOperationType(),
                expectedOperationType, fruitTransaction.getOperationType());

        Assert.assertEquals("Expected name: " + expectedName
                        + " but was " + fruitTransaction.getName(),
                expectedName, fruitTransaction.getName());

        Assert.assertEquals("Expected quantity: " + expectedQuantity
                        + " but was " + fruitTransaction.getQuantity(),
                expectedQuantity, fruitTransaction.getQuantity());
    }

    @Test
    public void add_nullOperationTypeToFruitTransaction_NotOk() {
        assertThrows(ValidationException.class, () ->
                new FruitTransaction(null, "name", 10));
    }

    @Test
    public void add_nullNameToFruitTransaction_NotOk() {
        assertThrows(ValidationException.class, () ->
                new FruitTransaction(OperationType.BALANCE, null, 10));
    }

    @Test
    public void add_noneOperationTypeToFruitTransaction_NotOk() {
        assertThrows(ValidationException.class, () ->
                new FruitTransaction(OperationType.NONE, "name", 10));
    }

    @Test
    public void add_emptyNameToFruitTransaction_NotOk() {
        assertThrows(ValidationException.class, () ->
                new FruitTransaction(OperationType.SUPPLY, "", 10));
    }

    @Test
    public void add_negativeQuantityToFruitTransaction_NotOk() {
        assertThrows(ValidationException.class, () ->
                new FruitTransaction(OperationType.SUPPLY, "name", -10));
    }
}
