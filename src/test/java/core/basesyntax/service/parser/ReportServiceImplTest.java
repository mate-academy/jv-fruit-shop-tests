package core.basesyntax.service.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.fruitmodel.Fruit;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String FRUIT_FIRST = "banana";
    private static final String FRUIT_SECOND = "apple";
    private static final String TITLE = "fruit,quantity";
    private static final Integer QUANTITY_FIRST = 125;
    private static final Integer QUANTITY_SECOND = 45;
    private static final String EXPECTED_STRING =
                "fruit,quantity" + System.lineSeparator()
                        + "banana,125" + System.lineSeparator()
                        + "apple,45" + System.lineSeparator();
    private static ReportService reportService;
    private static StringBuilder stringBuilder;

    @BeforeClass
    public static void beforeClass() {
        FruitStorage.fruitStorage.clear();
        reportService = new ReportServiceImpl();
        stringBuilder = new StringBuilder();
        stringBuilder.append(TITLE).append(System.lineSeparator());
    }

    @Test
    public void report_Ok() {
        FruitStorage.fruitStorage.put(new Fruit(FRUIT_FIRST), QUANTITY_FIRST);
        FruitStorage.fruitStorage.put(new Fruit(FRUIT_SECOND), QUANTITY_SECOND);
        for (Map.Entry<Fruit, Integer> entry : FruitStorage.fruitStorage.entrySet()) {
            stringBuilder.append(entry.getKey().getName())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        String expected = stringBuilder.toString();
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.fruitStorage.clear();
    }
}
