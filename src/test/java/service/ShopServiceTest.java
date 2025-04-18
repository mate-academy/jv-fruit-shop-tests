package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.List;
import model.Fruit;
import model.FruitTransaction;
import model.FruitTransaction.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.ShopServiceImpl;

public class ShopServiceTest {
    private static ShopService service;
    private static Storage storage;

    @BeforeEach
    void create() {
        service = new ShopServiceImpl();
        storage = new Storage();
    }

    @Test
    void processTransactions_SuccessfulExecution() {
        Fruit fruit = new Fruit("banana", 50);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(OperationType.BALANCE, fruit));
        service.processTransactions(transactions);
        assertEquals(50, storage.get(fruit.getName()));
    }

    @Test
    void processTransactions_withNullOperation_throwsException() {
        Fruit fruit = new Fruit("banana", 100);
        List<FruitTransaction> transactions = List.of(new FruitTransaction(null, fruit));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.processTransactions(transactions));
        Assertions.assertEquals("No handler for operation: null", exception.getMessage());
    }
}
