package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
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
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        map.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
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
