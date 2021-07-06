package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    @BeforeClass
    public static void beforeClass() {
        Storage.fruits.put(new Fruit("banana"), 20);
        Storage.fruits.put(new Fruit("apple"), 2056);
        Storage.fruits.put(new Fruit("cherry"), 2098);
    }

    @Test
    public void getReport_Ok() {
        StringBuilder builder = new StringBuilder()
                .append("fruit,quantity")
                .append(System.lineSeparator());
        for (Map.Entry<Fruit, Integer> pair : Storage.fruits.entrySet()) {
            builder.append(pair.getKey().getName())
                    .append(",")
                    .append(pair.getValue())
                    .append(System.lineSeparator());
        }
        String expected = builder.toString().trim();
        String actual = new ReportServiceImpl().getReport();
        assertEquals(expected, actual);
    }
}
