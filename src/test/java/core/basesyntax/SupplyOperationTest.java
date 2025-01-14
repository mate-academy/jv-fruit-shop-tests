package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static final String FRUIT_KEY = "banana";
    private static final String WRONG_KEY = "apple";

    private Storage storage;
    private OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        int startQuantity = 20;
        storage = new Storage();
        supplyOperation = new SupplyOperation();
        storage.set(FRUIT_KEY, startQuantity);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_supplyOperation_validItemAndQuantity_Ok() {
        int supplyQuantity = 40;
        int expectedQuantity = 60;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT_KEY, supplyQuantity);
        supplyOperation.handle(transaction, storage);
        Assertions.assertEquals(expectedQuantity, storage.getInventory().get(FRUIT_KEY));
    }

    @Test
    void handle_supplyOperation_notExistingItem_NotOk() {
        int supplyQuantity = 40;
        FruitTransaction wrongTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                WRONG_KEY, supplyQuantity);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> supplyOperation.handle(wrongTransaction, storage));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Fruit " + WRONG_KEY
                + " is not present in the inventory";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handle_supplyOperation_negativeQuantity_NotOk() {
        int negativeQuantity = -10;
        int expectedQuantity = 20;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT_KEY, negativeQuantity);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.handle(transaction, storage));
        String expectedMessage = "Quantity to add must be greater than 0";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedQuantity, storage.getInventory().get(FRUIT_KEY));
    }
}
