package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.DecreaseOperationHandler;
import core.basesyntax.strategy.impl.IncreaseOperationHandler;
import core.basesyntax.strategy.impl.SetOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private static FruitService fruitService;
    private Fruit apple;

    @BeforeAll
    static void beforeAll() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategy(Map.of(
                Operation.BALANCE, new SetOperationHandler(),
                Operation.PURCHASE, new DecreaseOperationHandler(),
                Operation.RETURN, new IncreaseOperationHandler(),
                Operation.SUPPLY, new IncreaseOperationHandler()
        ));
        fruitService = new FruitServiceImpl(fruitDao, operationStrategy);
    }

    @BeforeEach
    void setUp() {
        apple = new Fruit("apple");
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void updateFruitsInStock_RegularCase_Ok() {
        Fruit apple = new Fruit("apple");
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, apple, 10),
                new FruitTransaction(Operation.PURCHASE, apple, 5),
                new FruitTransaction(Operation.RETURN, apple, 10),
                new FruitTransaction(Operation.SUPPLY, apple, 10)
        );
        Integer expected = 25;
        fruitService.updateFruitsInStock(fruitTransactions);
        Integer actual = Storage.STORAGE.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    void updateFruitsInStock_EmptyList_Ok() {
        List<FruitTransaction> emptyList = List.of();
        Integer expected = 0;
        fruitService.updateFruitsInStock(emptyList);
        Integer actual = Storage.STORAGE.size();
        assertEquals(expected, actual);
    }

    @Test
    void updateFruitsInStock_ZeroQuantity_Ok() {
        List<FruitTransaction> testList = List.of(
                new FruitTransaction(Operation.BALANCE, apple, 0)
        );
        assertDoesNotThrow(() -> fruitService.updateFruitsInStock(testList));
    }

    @Test
    void updateFruitsInStock_ZeroQuantityAfterPurchase_Ok() {
        Storage.STORAGE.put(apple, 10);
        List<FruitTransaction> testList = List.of(
                new FruitTransaction(Operation.PURCHASE, apple, 10)
        );
        assertDoesNotThrow(() -> fruitService.updateFruitsInStock(testList),
                "apple quantity after purchase must be 0");
        assertEquals(0, Storage.STORAGE.get(apple),
                "must be 0 apple in storage");
    }

    @Test
    void updateFruitsInStock_moreThanAvailable_NotOk() {
        List<FruitTransaction> testList = List.of(
                new FruitTransaction(Operation.BALANCE, new Fruit("banana"), 10),
                new FruitTransaction(Operation.PURCHASE, new Fruit("banana"), 11)
        );
        assertThrows(RuntimeException.class, () -> fruitService.updateFruitsInStock(testList),
                "User wants to buy more products than are available in stock");
    }

    @Test
    void updateFruitsInStock_negativeQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            List<FruitTransaction> testList = List.of(
                    new FruitTransaction(Operation.BALANCE, apple, -10)
            );
            fruitService.updateFruitsInStock(testList);
        }, "fruit with negative quantity can't be updated");
    }
}
