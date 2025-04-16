package service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import model.Fruit;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.ShopServiceImpl;

public class ShopServiceTest {
    private static ShopService service;
    private static Fruit fruit;

    @BeforeEach
    void create() {
        service = new ShopServiceImpl();
    }

    @Test
    void processTransactions_withNullOperation_throwsException() {
        fruit = new Fruit("banana", 100);
        List<FruitTransaction> transactions = List.of(new FruitTransaction(null, fruit));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.processTransactions(transactions));
        Assertions.assertEquals("No handler for operation: null", exception.getMessage());
    }
}
