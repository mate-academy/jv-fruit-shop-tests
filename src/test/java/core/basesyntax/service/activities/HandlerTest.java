package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HandlerTest {
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static Fruit banana;
    private static Handler handler;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        banana = new Fruit();
    }

    @Before
    public void setUp() {
        banana.setFruitName("banana");
        banana.setFruitQuantity(10);
        FruitStorage.fruits.add(banana);
    }

    @Test
    public void validate_BalanceHandler() {
        handler = new BalanceHandler();
        String[] data = new String[]{"b", "banana", "30"};
        handler.createFruitObject(data);
        assertEquals("banana", FruitStorage.fruits.get(SECOND_INDEX).getFruitName());
        assertEquals(30, FruitStorage.fruits.get(SECOND_INDEX).getFruitQuantity());
    }

    @Test
    public void validate_PurchaseHandler_FruitShortage() {
        handler = new PurchaseHandler();
        String[] data = new String[]{"b", "banana", "20"};
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Not enough banana to buy");
        handler.createFruitObject(data);
    }

    @Test
    public void incorrectFruit_NotOk() {
        handler = new PurchaseHandler();
        String[] data = new String[]{"b", "pineapple", "20"};
        expectedEx.expect(NoSuchElementException.class);
        expectedEx.expectMessage("No value present");
        handler.createFruitObject(data);
    }

    @Test
    public void validate_PurchaseHandler() {
        handler = new PurchaseHandler();
        String[] data = new String[]{"b", "banana", "5"};
        handler.createFruitObject(data);
        assertEquals("banana", FruitStorage.fruits.get(FIRST_INDEX).getFruitName());
        assertEquals(5, FruitStorage.fruits.get(FIRST_INDEX).getFruitQuantity());
    }

    @Test
    public void validate_ReturnHandler() {
        handler = new ReturnHandler();
        String[] data = new String[]{"b", "banana", "5"};
        handler.createFruitObject(data);
        assertEquals("banana", FruitStorage.fruits.get(FIRST_INDEX).getFruitName());
        assertEquals(15, FruitStorage.fruits.get(FIRST_INDEX).getFruitQuantity());
    }

    @Test
    public void validate_SupplyHandler() {
        handler = new SupplyHandler();
        String[] data = new String[]{"b", "banana", "5"};
        handler.createFruitObject(data);
        assertEquals("banana", FruitStorage.fruits.get(FIRST_INDEX).getFruitName());
        assertEquals(15, FruitStorage.fruits.get(FIRST_INDEX).getFruitQuantity());
    }

    @After
    public void afterTest() {
        FruitStorage.fruits.clear();
    }
}
