package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageReportGeneratorTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_PEACH = "peach";
    private static final int QUANTITY_FOR_APPLE = 12;
    private static final int QUANTITY_FOR_BANANA = 11;
    private static final int QUANTITY_FOR_PEACH = 33;
    private static final String REPORT_HEADER = "fruit,quantity" + System.lineSeparator();
    private final StorageReportGenerator storageReportGenerator = new StorageReportGeneratorImpl();

    @BeforeEach
    public void beforeEach() {
        Storage.setFruitStorage(new TreeMap<>());
    }

    @Test
    public void storageReportGenerator_EmptyStorage_Ok() {
        assertEquals(REPORT_HEADER, storageReportGenerator.getReport());
    }

    @Test
    public void storageReportGenerator_StorageWithData_Ok() {
        Map<String, Integer> testMap = new TreeMap<>();
        testMap.put(FRUIT_APPLE, QUANTITY_FOR_APPLE);
        testMap.put(FRUIT_BANANA, QUANTITY_FOR_BANANA);
        testMap.put(FRUIT_PEACH, QUANTITY_FOR_PEACH);
        Storage.setFruitStorage(testMap);
        StringBuilder assertString = new StringBuilder(REPORT_HEADER);
        assertString.append(FRUIT_APPLE).append(",")
                .append(QUANTITY_FOR_APPLE).append(System.lineSeparator());
        assertString.append(FRUIT_BANANA).append(",")
                .append(QUANTITY_FOR_BANANA).append(System.lineSeparator());
        assertString.append(FRUIT_PEACH).append(",")
                .append(QUANTITY_FOR_PEACH).append(System.lineSeparator());
        assertEquals(assertString.toString(), storageReportGenerator.getReport());
    }
}
