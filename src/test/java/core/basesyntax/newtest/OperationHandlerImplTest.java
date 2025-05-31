package core.basesyntax.newtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerImplTest {
    private OperationHandlerImpl handler;

    @BeforeEach
    public void setUp() {
        handler = new OperationHandlerImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    public void handle_balanceOperation_setsQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);

        handler.handle(transaction);

        assertEquals(20, Storage.fruitStorage.get("banana"));
    }

    @Test
    public void handle_purchaseOperation_decreasesQuantity() {
        Storage.fruitStorage.put("banana", 30);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(10);

        handler.handle(transaction);

        assertEquals(20, Storage.fruitStorage.get("banana"));
    }

    @Test
    public void handle_purchaseOperation_throwsIfNotEnoughStock() {
        Storage.fruitStorage.put("apple", 5);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(transaction);
        });

        assertTrue(exception.getMessage().contains("Not enough stock"));
    }

    @Test
    public void handle_supplyOperation_increasesQuantity() {
        Storage.fruitStorage.put("orange", 5);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("orange");
        transaction.setQuantity(10);

        handler.handle(transaction);

        assertEquals(15, Storage.fruitStorage.get("orange"));
    }

    @Test
    public void handle_returnOperation_increasesQuantity() {
        Storage.fruitStorage.put("pear", 3);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("pear");
        transaction.setQuantity(2);

        handler.handle(transaction);

        assertEquals(5, Storage.fruitStorage.get("pear"));
    }
}
