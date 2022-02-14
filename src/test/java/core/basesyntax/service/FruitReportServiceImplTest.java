package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.inter.FruitReportService;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportServiceImplTest {
    private static FruitReportService fruitReportService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitReportService = new FruitReportServiceImpl();
    }

    @Test
    public void createReportCorrectValues_Ok() {
        String actual = fruitReportService.createReport();
        StringBuilder result = new StringBuilder("fruit, quantity" + "\n");
        for (Map.Entry<String, Integer> fruit: Storage.fruitsQuantity.entrySet()) {
            result.append(fruit).append("\n");
        }
        String expected = result.toString();
        assertEquals(expected, actual);
    }
}
