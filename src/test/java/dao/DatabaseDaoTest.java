package dao;

import db.Database;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseDaoTest {
    private static DatabaseDao dao;

    @BeforeClass
    public static void beforeClass() {
        dao = new DatabaseDaoImpl();
    }

    @Test
    public void database_AddFruit_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("b"), "apple", 16);
        dao.addFruit(transaction.getFruit(), transaction.getQuantity());
        int expected = 16;
        int actual = Database.database.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void database_GetAllFruits_OK() {
        Database.database.put("orange", 54);
        Database.database.put("apple", 75);
        Map<String, Integer> expected = Map.of("orange", 54, "apple", 75);
        Map<String, Integer> actual = dao.getAllFruits();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Database.database.clear();
    }
}
