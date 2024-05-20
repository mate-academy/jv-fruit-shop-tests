package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Main;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitServiceImplTest {
    private static FruitServiceImpl service;
    private static FruitDao fruitDao;
    private static EnumMap<Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy strategy;

    @BeforeAll
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        operationHandlerMap = new EnumMap<>(Operation.class);
        Main.fillOperationMap(operationHandlerMap);
        strategy = new OperationStrategyImpl(operationHandlerMap);
        service = new FruitServiceImpl(fruitDao, strategy);
    }

    @AfterEach
    public void cleanStorage() {
        Storage.fruits.clear();
    }

    @Test
    void get_operationCount_ok() {
        assertEquals(6, service.getOperatedCount(2, 4, Operation.BALANCE));
    }

    @Test
    void get_operationCountWithNullOperation_ok() {
        assertThrows(NullPointerException.class, () -> service.getOperatedCount(2, 4, null));
    }

    @Test
    void create_newFruit_ok() {
        Fruit fruit = new Fruit("apple", 23);
        service.createNewFruit("apple", 23);

        assertEquals(fruit, Storage.fruits.get(0));
    }

    @Test
    void create_fromList_ok() {
        List<String> expectedRows = new ArrayList<>();
        expectedRows.add("b,banana,20");
        expectedRows.add("b,apple,100");
        expectedRows.add("s,banana,100");

        service.createFruitsFromList(expectedRows);
        assertEquals(2, Storage.fruits.size());
    }

    @Test
    void get_count_ok() {
        List<String> expectedRows = new ArrayList<>();
        expectedRows.add("b,banana,20");
        expectedRows.add("b,apple,100");
        expectedRows.add("s,banana,100");

        service.createFruitsFromList(expectedRows);
        int bananaIndex = 1;
        assertEquals(120, Storage.fruits.get(bananaIndex).getQuantity());
    }
}
