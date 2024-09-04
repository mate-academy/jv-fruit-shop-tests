package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.dao.FruitDao;
import core.basesyntax.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.Fruit;
import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.FruitServiceImpl;
import core.basesyntax.basesyntax.service.operations.BalanceOperationsHandler;
import core.basesyntax.basesyntax.service.operations.PurchaseOperationsHandler;
import core.basesyntax.basesyntax.service.operations.SupplyOperationsHandler;
import core.basesyntax.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.basesyntax.service.strategy.OperationsStrategy;
import core.basesyntax.basesyntax.service.strategy.OperationsStrategyImpl;
import core.basesyntax.basesyntax.service.strategy.ReturnOperationHandler;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitServiceImplTest {
    private static FruitServiceImpl service;
    private static EnumMap<Operations, OperationHandler> operationHandlerMap;
    private static FruitDao fruitDao;
    private static OperationsStrategy operationsStrategy;

    @BeforeAll
    public static void setUp() {
        operationHandlerMap = new EnumMap<>(Operations.class);
        operationHandlerMap.put(Operations.BALANCE, new BalanceOperationsHandler());
        operationHandlerMap.put(Operations.SUPPLY, new SupplyOperationsHandler());
        operationHandlerMap.put(Operations.PURCHASE, new PurchaseOperationsHandler());
        operationHandlerMap.put(Operations.RETURN, new ReturnOperationHandler());

        operationsStrategy = new OperationsStrategyImpl(operationHandlerMap);
        fruitDao = new FruitDaoImpl();
        service = new FruitServiceImpl(fruitDao, operationsStrategy);
    }

    @AfterEach
    public void cleanStorage() {
        Storage.getFruits().clear();
    }

    @Test
    void performOperation_Ok() {
        assertEquals(11, service.performOperation(3, 8, Operations.BALANCE));
    }

    @Test
    void performOperationWhenIsNullOperation_Ok() {
        assertThrows(UnsupportedOperationException.class,
                () -> service.performOperation(6, 8, null));
    }

    @Test
    void addFruitFromList_shouldAddAndUpdateFruitsCorrectly() {
        String separator = ",";
        String[] splitRecord1 = {"s", "apple", "5"};
        String[] splitRecord2 = {"p", "banana", "3"};

        Fruit apple = new Fruit("apple", 10);
        Fruit banana = new Fruit("banana", 8);

        Storage.addOrUpdateFruit(apple.getName(), apple);
        Storage.addOrUpdateFruit(banana.getName(), banana);

        List<String> fruitsDocumentation = List.of(
                String.join(separator, splitRecord1),
                String.join(separator, splitRecord2)
        );

        service.addFruitFromList(fruitsDocumentation);

        assertEquals(new Fruit("apple", 15), Storage.getFruit("apple"));
        assertEquals(new Fruit("banana", 5), Storage.getFruit("banana"));
    }

    @Test
    void get_count_ok() {
        List<String> expectedRows = List.of(
                "s,apple,100",
                "b,apple,100",
                "r,banana,20"
        );

        service.addFruitFromList(expectedRows);

        Fruit apple = Storage.getFruits().get("apple");
        assertNotNull(apple, "Apple should not be null");

        assertEquals(235, apple.getQuantity(), "Apple quantity should be 235");
    }
}
