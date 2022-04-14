package core.basesyntax.services.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class IncreaseFruitHandlerTest {
    private static FruitHandler increaseFruitHandler;
    private static Map<Fruit, Integer> fruitStorage;

    @Before
    public void setUp() {
        increaseFruitHandler = new IncreaseFruitHandler();
        fruitStorage = new HashMap<>();
        fruitStorage.put(new Fruit("banana"), 152);
        fruitStorage.put(new Fruit("apple"), 90);
    }

    @Test(expected = ValidationException.class)
    public void newQuantity_NotOk() {
        Operation operation = new Operation("s", new Fruit("orange"), 100);
        increaseFruitHandler.newQuantity(operation,fruitStorage);
    }

    @Test()
    public void newQuantity_Ok() {
        Operation operation = new Operation("s", new Fruit("banana"), 100);
        int actual = increaseFruitHandler.newQuantity(operation, fruitStorage);
        int expected = 252;
        assertEquals("Test failed: wrong quantity", expected,actual);
    }
}
