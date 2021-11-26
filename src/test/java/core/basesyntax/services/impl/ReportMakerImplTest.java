package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportMaker;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerImplTest {
    private static ReportMaker reportMaker;

    @BeforeClass
    public static void beforeClass() {
        reportMaker = new ReportMakerImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String actual = reportMaker.createReport();
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_tenElements_Ok() {
        for (int i = 0; i <= 100; i += 10) {
            String fruitName = "Fruit" + i / 10;
            Integer quantity = i;
            Storage.storage.put(fruitName, quantity);
        }
        String report = reportMaker.createReport();
        String[] lines = report.split(System.lineSeparator());
        assertEquals("fruit,quantity", lines[0]);
        int index = 1;
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            String[] dividedData = lines[index].split(",");
            String actualName = dividedData[0];
            Integer actualQuantity = Integer.parseInt(dividedData[1]);
            assertEquals("Wrong fruit name on index " + index,
                    entry.getKey(), actualName);
            assertEquals("Wrong quantity on index " + index,
                    entry.getValue(), actualQuantity);
            index++;
        }
    }
}
