package service;

import model.FruitTransaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FruitModelTests {

    @Test
    public void constructor_ShouldSetFieldsCorrectly_WhenAllParamsProvided() {
        FruitTransaction transaction =
            new FruitTransaction("Apple", 50, FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals("Apple", transaction.getFruit());
        Assertions.assertEquals(50, transaction.getQuantity());
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    public void constructor_ShouldSetFieldsCorrectly_WhenNoOperationProvided() {
        FruitTransaction transaction = new FruitTransaction("Banana", 30, null);
        Assertions.assertEquals("Banana", transaction.getFruit());
        Assertions.assertEquals(30, transaction.getQuantity());
        Assertions.assertNull(transaction.getOperation());
    }

    @Test
    public void setters_ShouldUpdateValuesCorrectly() {
      FruitTransaction transaction =
          new FruitTransaction("Orange", 20, FruitTransaction.Operation.RETURN);
      transaction.setOperation(FruitTransaction.Operation.PURCHASE);
      transaction.setFruit("Mango");
      transaction.setQuantity(40);

      Assertions.assertEquals("Mango", transaction.getFruit());
      Assertions.assertEquals(40, transaction.getQuantity());
      Assertions.assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
    }

    @Test
    public void operationEnum_ShouldReturnCorrectOperation_WhenValidCode() {
        Assertions.assertEquals(
            FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        Assertions.assertEquals(
            FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        Assertions.assertEquals(
            FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        Assertions.assertEquals(
            FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    public void operationEnum_ShouldThrowException_WhenInvalidCode() {
      Exception exception =
          Assertions.assertThrows(
              IllegalArgumentException.class,
                () -> {
                FruitTransaction.Operation.fromCode("x");
              });
      Assertions.assertTrue(exception.getMessage().contains("Unknown transaction type: x"));
    }
}
