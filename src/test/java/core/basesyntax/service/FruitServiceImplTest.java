package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    private static final OperationStrategy OPERATION_STRATEGY = new OperationStrategy(PRODUCT_DAO);
    private static FruitService fruitService;
    
    @BeforeAll
    static void setUp() {
        fruitService = new FruitServiceImpl(OPERATION_STRATEGY);
    }

    @Test
    void process_withNullInitializationListOfTransaction_notOk() {
        List<FruitTransaction> fruitTransactions = null;
        assertThrows(NullPointerException.class, () -> fruitService.process(fruitTransactions));
    }

    @Test
    void process_withEmptyInitializationListOfTransactionShouldNotChangeTheStorage_ok() {
        List<FruitTransaction> fruitTransactions = Collections.emptyList();
        FruitsStorage.FRUIT_MAP.put("banana", 10);
        int expected = FruitsStorage.FRUIT_MAP.get("banana");
        int expectedSize = FruitsStorage.FRUIT_MAP.size();
        fruitService.process(fruitTransactions);
        int actual = FruitsStorage.FRUIT_MAP.get("banana");
        int actualSize = FruitsStorage.FRUIT_MAP.size();
        assertEquals(expected, actual);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void process_addQuantityToStorage_ok() {
        FruitsStorage.FRUIT_MAP.put("apple", 0);
        FruitsStorage.FRUIT_MAP.put("banana", 0);
        int expectedQuantityOfApple = 100;
        int expectedQuantityOfBanana = 100;
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 100));
        fruitService.process(fruitTransactions);
        int actualQuantityOfApple = FruitsStorage.FRUIT_MAP.get("apple");
        int actualQuantityOfBanana = FruitsStorage.FRUIT_MAP.get("banana");
        assertEquals(expectedQuantityOfApple, actualQuantityOfApple);
        assertEquals(expectedQuantityOfBanana, actualQuantityOfBanana);
    }

    @Test
    void process_subQuantityFromStorage_ok() {
        FruitsStorage.FRUIT_MAP.put("apple", 100);
        FruitsStorage.FRUIT_MAP.put("banana", 100);
        int expectedQuantityOfApple = 0;
        int expectedQuantityOfBanana = 0;
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 100));
        fruitService.process(fruitTransactions);
        int actualQuantityOfApple = FruitsStorage.FRUIT_MAP.get("apple");
        int actualQuantityOfBanana = FruitsStorage.FRUIT_MAP.get("banana");
        assertEquals(expectedQuantityOfApple, actualQuantityOfApple);
        assertEquals(expectedQuantityOfBanana, actualQuantityOfBanana);
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUIT_MAP.clear();
    }
}
