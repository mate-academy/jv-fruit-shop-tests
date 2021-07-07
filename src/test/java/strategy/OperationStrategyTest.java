package strategy;

import db.FruitsDao;
import db.GenericDao;
import models.Fruit;
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class OperationStrategyTest {
    private static GenericDao<Fruit, Integer> dao = new FruitsDao();
    private static Map<String, OperationHandler> operationsMap = Map.of(
            "b", new AdditionalOperationHandler(dao),
            "p", new PurchaseOperationHandler(dao));
    private static OperationStrategy operationStrategy = new OperationStrategy(operationsMap);

    @Test
    public void getExistOperationStrategy_Ok() {
        assertEquals(operationsMap.get("b"), operationStrategy.getOperationStrategy("b"));
    }

    @Test(expected = RuntimeException.class)
    public void getNotExistOperationStrategy_NotOk() {
        operationStrategy.getOperationStrategy("d");
    }

    @Test
    public void operationExist_Ok() {
        boolean result = operationStrategy.isOperationExist("b");
        assertEquals(true, result);
    }

    @Test
    public void operationIsNotExist_Ok() {
        boolean result = operationStrategy.isOperationExist("d");
        assertEquals(false, result);
    }
}