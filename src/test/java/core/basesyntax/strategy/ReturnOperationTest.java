package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static final String FRUIT_KEY = "banana";
    private static final String WRONG_KEY = "apple";

    private Storage storage;
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        int startQuantity = 50;
        storage = new Storage();
        storage.set(FRUIT_KEY, startQuantity);
        returnOperation = new ReturnOperation();
    }

    @Test
    void handle_returnOperation_Ok() {
        int returnQuantity = 10;
        int expectedQuantity = 60;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_KEY, returnQuantity);
        returnOperation.handle(transaction, storage);
        Assertions.assertEquals(expectedQuantity, storage.getInventory().get(FRUIT_KEY));
    }

    @Test
    void handle_returnOperation_notExistingItem_NotOk() {
        int purchaseQuantity = 40;
        FruitTransaction wrongTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                WRONG_KEY, purchaseQuantity);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> returnOperation.handle(wrongTransaction, storage));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Fruit " + WRONG_KEY
                + " is not present in the inventory";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handle_returnOperation_negativeQuantity_NotOk() {
        int negativeQuantity = -10;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_KEY, negativeQuantity);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> returnOperation.handle(transaction, storage));
        String expectedMessage = "Quantity to add must be greater than 0";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
