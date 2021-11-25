package service.action.type;

import bd.LocalStorage;
import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.action.ActionStrategyHandler;

public class MinusQuantityHandlerTest {
    private static ActionStrategyHandler handler;
    private static List<Fruit> storage;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        handler = new MinusQuantityHandler();
        LocalStorage.fruits.add(new Fruit("apple", 100));
        storage = new ArrayList<>();
    }

    @Test
    public void minusQuantity_invalidAmount_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("the quantity of goods cannot be negative");
        handler.doing("apple", 200);
    }

    @Test
    public void minusQuantity_nonExistentFruit_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("there is no such vegetable to sell");
        handler.doing("banana", 20_000);
    }

    @Test
    public void minusQuantity_validData_ok() {
        storage.add(new Fruit("apple", 10));
        handler.doing("apple", 90);
        Assert.assertEquals(storage, LocalStorage.fruits);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        LocalStorage.fruits.clear();
    }
}
