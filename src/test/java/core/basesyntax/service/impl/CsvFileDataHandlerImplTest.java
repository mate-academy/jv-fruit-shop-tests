package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.OperationStrategy;
import core.basesyntax.operations.OperationStrategyImpl;
import core.basesyntax.operations.impl.BalanceHandler;
import core.basesyntax.operations.impl.PurchaseHandler;
import core.basesyntax.operations.impl.ReturnHandler;
import core.basesyntax.operations.impl.SupplyHandler;
import core.basesyntax.service.CsvFileDataHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileDataHandlerImplTest {
    private static List<String> fileData;
    private static FruitDao fruitDao;
    private static CsvFileDataHandler dataHandler;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        Map<String, OperationHandler> operations = new HashMap<>();
        operations.put("b", new BalanceHandler(fruitDao));
        operations.put("s", new SupplyHandler(fruitDao));
        operations.put("r", new ReturnHandler(fruitDao));
        operations.put("p", new PurchaseHandler(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operations);
        dataHandler = new CsvFileDataHandlerImpl(operationStrategy);
        fileData = new ArrayList<>();
        fileData.add("type,fruit,quantity");
    }

    @Test
    public void processData_checkAllOperations_ok() {
        fileData.add("b,banana,20");
        fileData.add("b,apple,100");
        fileData.add("s,banana,100");
        fileData.add("p,banana,13");
        fileData.add("r,apple,10");
        fileData.add("p,apple,20");
        fileData.add("p,banana,5");
        fileData.add("s,banana,50");
        dataHandler.processData(fileData);
        assertEquals(152, fruitDao.get("banana"));
        assertEquals(90, fruitDao.get("apple"));
        assertEquals(2, fruitDao.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void processData_tooMuchPurchase_notOk() {
        fileData.add("p,banana,200");
        dataHandler.processData(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void processData_nonexistentFruitPurchase_notOk() {
        fileData.add("p,strawberry,20");
        dataHandler.processData(fileData);
    }

    @After
    public void afterEachTest() {
        fileData.remove(fileData.size() - 1);
        Storage.fruits.clear();
    }
}
