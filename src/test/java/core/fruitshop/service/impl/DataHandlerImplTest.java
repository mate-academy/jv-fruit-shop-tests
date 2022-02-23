package core.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.fruitshop.dao.FruitDao;
import core.fruitshop.dao.FruitDaoImpl;
import core.fruitshop.db.Storage;
import core.fruitshop.model.Fruit;
import core.fruitshop.model.FruitTransaction;
import core.fruitshop.model.FruitTransaction.Operation;
import core.fruitshop.service.DataHandler;
import core.fruitshop.service.strategy.OperationHandler;
import core.fruitshop.service.strategy.OperationStrategy;
import core.fruitshop.service.strategy.impl.BalanceOperationHandler;
import core.fruitshop.service.strategy.impl.OperationStrategyImpl;
import core.fruitshop.service.strategy.impl.PurchaseOperationHandler;
import core.fruitshop.service.strategy.impl.ReturnOperationHandler;
import core.fruitshop.service.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataHandlerImplTest {

    private static DataHandler dataHandler;
    private static Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;

    @BeforeClass
    public static void beforeClass() {
        FruitDao dao = new FruitDaoImpl();
        map.put(Operation.BALANCE, new BalanceOperationHandler(dao));
        map.put(Operation.PURCHASE, new PurchaseOperationHandler(dao));
        map.put(Operation.RETURN, new ReturnOperationHandler(dao));
        map.put(Operation.SUPPLY, new SupplyOperationHandler(dao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(map);
        dataHandler = new DataHandlerImpl(operationStrategy);
    }

    @Test
    public void processData_validData_ok() {
        List<String> incomingData = List.of("b,banana,50", "b,apple,100",
                "s,apple,100", "p,banana,30");
        dataHandler.processData(incomingData);
        Map<Fruit, Integer> mapExpected = new HashMap<>();
        mapExpected.put(new Fruit("banana"), 20);
        mapExpected.put(new Fruit("apple"), 200);
        assertEquals(mapExpected, fruitsStorage);
    }

    @Test
    public void processData_emptyData_ok() {
        List<String> incomingData = new ArrayList<>();
        dataHandler.processData(incomingData);
        assertTrue(fruitsStorage.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void processData_invalidData_notOk() {
        List<String> incomingData = List.of("String with invalid data");
        dataHandler.processData(incomingData);
    }

    @Test(expected = NullPointerException.class)
    public void processData_nullData_notOk() {
        List<String> incomingData = null;
        dataHandler.processData(incomingData);
    }

    @After
    public void tearDown() {
        fruitsStorage.clear();
    }
}
