package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static ProductDao productDao;
    private static OperationStrategy operationStrategy;
    private static FruitService fruitService;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        operationStrategy = new OperationStrategy(productDao);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Test
    void process_withNullInitializationList_notOk() {
        List<FruitTransaction> fruitTransactions = null;
        assertThrows(NullPointerException.class, ()
                -> fruitService.processTransaction(fruitTransactions));
    }

    @Test
    void process_withEmptyInitializationListShouldNotChangeTheStorage_ok() {
        List<FruitTransaction> fruitTransactions = Collections.emptyList();
        FruitStorage.Storage_Map.put("banana", 10);
        int expected = FruitStorage.Storage_Map.get("banana");
        int expectedSize = FruitStorage.Storage_Map.size();
        fruitService.processTransaction(fruitTransactions);
        int actual = FruitStorage.Storage_Map.get("banana");
        int actualSize = FruitStorage.Storage_Map.size();
        assertEquals(expected, actual);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void process_addQuantityToStorage_ok() {
        FruitStorage.Storage_Map.put("apple", 0);
        FruitStorage.Storage_Map.put("banana", 0);
        int expectedQuantityOfApple = 100;
        int expectedQuantityOfBanana = 100;
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(Operation.RETURN, "banana", 100),
                new FruitTransaction(Operation.RETURN, "apple", 100));
        fruitService.processTransaction(fruitTransactions);
        int actualQuantityOfApple = FruitStorage.Storage_Map.get("apple");
        int actualQuantityOfBanana = FruitStorage.Storage_Map.get("banana");
        assertEquals(expectedQuantityOfApple, actualQuantityOfApple);
        assertEquals(expectedQuantityOfBanana, actualQuantityOfBanana);
    }

    @Test
    void process_subQuantityFromStorage_ok() {
        FruitStorage.Storage_Map.put("apple", 100);
        FruitStorage.Storage_Map.put("banana", 100);
        int expectedQuantityOfApple = 0;
        int expectedQuantityOfBanana = 0;
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(Operation.PURCHASE, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "apple", 100));
        fruitService.processTransaction(fruitTransactions);
        int actualQuantityOfApple = FruitStorage.Storage_Map.get("apple");
        int actualQuantityOfBanana = FruitStorage.Storage_Map.get("banana");
        assertEquals(expectedQuantityOfApple, actualQuantityOfApple);
        assertEquals(expectedQuantityOfBanana, actualQuantityOfBanana);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.Storage_Map.clear();
    }
}
