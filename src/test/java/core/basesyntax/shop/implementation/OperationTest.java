package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTest {
    private static Operation operation;
    private static StringSplitter splitter;
    private static final String SPLIT_LINE = "b,apple,100";

    @BeforeClass
    public static void beforeClass() {
        splitter = new StringSplitter(SPLIT_LINE);
        operation = new Operation();
        Storage.fruits.clear();
    }

    @Test
    public void operateData_Ok() {
        operation.operateData(splitter);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 100);
        assertEquals(expected, Storage.getFruits());
    }
}
