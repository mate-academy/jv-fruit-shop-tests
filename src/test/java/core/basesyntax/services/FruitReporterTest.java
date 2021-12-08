package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.impl.FruitReporterImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReporterTest {
    private static FruitReporter fruitReporter;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @BeforeClass
    public static void setup() {
        fruitReporter = new FruitReporterImpl();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void report_withValuesInCorrectOrder_OK() {
        Storage.fruitsStorage.put(Fruit.APPLE,100);
        Storage.fruitsStorage.put(Fruit.BANANA, 20);
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,100" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR;
        String actual = fruitReporter.report(Storage.fruitsStorage);
        assertEquals(expected, actual);
    }

    @Test
    public void report_withValuesInWrongOrder_OK() {
        Storage.fruitsStorage.put(Fruit.BANANA, 20);
        Storage.fruitsStorage.put(Fruit.APPLE,100);
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,100" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR;
        String actual = fruitReporter.report(Storage.fruitsStorage);
        assertEquals(expected, actual);
    }
}
