package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDataHandlerTest {
    private static FruitDataHandler fruitDataHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDataHandler = new FruitDataHandler(new OperationStrategyImpl());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void processData_null_notOk() {
        fruitDataHandler.processData(null);
    }

    @Test
    public void processData_apple_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,apple,100");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("s,apple,50");

        fruitDataHandler.processData(data);
        Integer actual = Storage.fruits.get("apple");
        assertEquals(Integer.valueOf(140), actual);
    }

    @Test
    public void processData_banana_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,50");
        data.add("r,banana,20");
        data.add("p,banana,40");
        data.add("s,banana,30");

        fruitDataHandler.processData(data);
        Integer actual = Storage.fruits.get("banana");
        assertEquals(Integer.valueOf(60), actual);
    }

    @Test(expected = RuntimeException.class)
    public void processData_notOk() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,50");
        data.add("p,banana,60");
        fruitDataHandler.processData(data);
    }
}
