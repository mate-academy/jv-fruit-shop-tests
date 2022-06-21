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

public class SetBalanceOperationHandlerTest {
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
    public void doTransaction_balanceOperationSetQuantity_ok() {
        String message = "something wrong";
        storage.put("fungi", 150);
        OperationHandler balanceOperationHandler = new SetBalanceOperationHandler(fruitDao);
        balanceOperationHandler.doTransaction(new FruitTransaction("s", "fungi", 50));
        assertEquals(message, 50, fruitDao.get("fungi").intValue());
    }

    @After
    public void clearStorage() {
        storage.clear();
    }
}
