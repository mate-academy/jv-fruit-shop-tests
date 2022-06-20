package strategy;

import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.OperationHandler;

public class AddOperationHandlerTest {
    private static FruitDao fruitDao;
    private static Map<String, Integer> storage;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        storage = new HashMap<>();
        fruitDao = new FruitDaoImpl(storage);
    }

    @Test
    public void doTransaction_supplyOperationsWorkRight_ok() {
        String message = "expected quantity and actual are different";
        OperationHandler operationHandler = new AddOperationHandler(fruitDao);
        operationHandler.doTransaction(new FruitTransaction("s", "watermelon", 10));
        assertEquals(message, 10, fruitDao.get("watermelon").intValue());
    }

    @Test
    public void doTransaction_returnOperationsWorkRight_ok() {
        String message = "expected quantity and actual are different";
        storage.put("pineapple", 50);
        OperationHandler operationHandler = new AddOperationHandler(fruitDao);
        operationHandler.doTransaction(new FruitTransaction("r", "pineapple", 20));
        assertEquals(message, 70, fruitDao.get("pineapple").intValue());
    }

    @After
    public void clearStorage() {
        storage.clear();
    }
}
