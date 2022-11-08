package myfirstproject.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myfirstproject.dao.FruitDao;
import myfirstproject.dao.impl.FruitDaoImpl;
import myfirstproject.model.Operation;
import myfirstproject.service.ReadFile;
import myfirstproject.service.WriteFile;
import myfirstproject.strategy.BalanceOperation;
import myfirstproject.strategy.OperationHandler;
import myfirstproject.strategy.PurchaseOperation;
import myfirstproject.strategy.ReturnOperation;
import myfirstproject.strategy.SeparationOfOperations;
import myfirstproject.strategy.SupplyOperation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class InitializingTest {
    private static final String PATH_TO_DB = "src/main/resources/sourceFile.csv";
    private static final String PATH_TO_RESULT = "src/main/resources/resultFile.csv";
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final ReadFile reader = new ReadFileImpl();
    private static final WriteFile writer = new WriteFileImpl();
    private static final SeparationOfOperations separation = new SeparationOfOperations();
    private static final List<String[]> temporalList = new ArrayList<>();
    private static Map<String, OperationHandler> operation;

    @BeforeClass
    public static void before() {
        operation = new HashMap<>();
    }

    @Test
    public void testInitializedOperations_ok() {
        operation.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operation.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        operation.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        operation.put(Operation.RETURN.getOperation(), new ReturnOperation());
        reader.readFile(temporalList, PATH_TO_DB);
        separation.toDoEachOperation(fruitDao, temporalList, operation);
        writer.writeToFile(PATH_TO_RESULT, fruitDao.getAll());
        Assert.assertNotNull(operation);
    }

    @Test
    public void testBalanceIsNull_ok() {
        Assert.assertNull(operation.get(Operation.BALANCE.getOperation()));
    }

    @Test
    public void testSupplyIsNull_ok() {
        Assert.assertNull(operation.get(Operation.SUPPLY.getOperation()));
    }

    @Test
    public void testPurchaseIsNull_ok() {
        Assert.assertNull(operation.get(Operation.PURCHASE.getOperation()));
    }

    @Test
    public void testReturnIsNull_ok() {
        Assert.assertNull(operation.get(Operation.RETURN.getOperation()));
    }

    @AfterAll
    public static void after() {
        operation.clear();
    }
}
