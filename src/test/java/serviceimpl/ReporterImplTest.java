package serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Reporter;

class ReporterImplTest {
    private static final String FIRST_STRING = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final Integer AMOUNT_APPLE = 10;
    private static final Integer AMOUNT_ORANGE = 15;
    private static Reporter reporter;

    @BeforeAll
    public static void setUpClass() {
        reporter = new ReporterImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.clearStorage();
        Storage.storeFruit(APPLE, AMOUNT_APPLE);
        Storage.storeFruit(ORANGE, AMOUNT_ORANGE);
    }

    @Test
    public void processDailyReport_validData_ok() {
        List<String> storage = Storage
                .getStorage().entrySet()
                .stream()
                .map(t -> t.getKey()
                        + FruitPackerImpl.COMMA
                        + t.getValue()).collect(Collectors.toList());
        storage.add(0,FIRST_STRING);
        assertEquals(storage, reporter.report());
    }

    @Test
    public void processDailyReport_emptyStorage() {
        Storage.clearStorage();
        List<String> expected = Collections.singletonList(FIRST_STRING);
        assertEquals(expected, reporter.report());
    }

    @Test
    public void processDailyReport_multipleFruits() {
        Storage.clearStorage();
        Storage.storeFruit(APPLE, AMOUNT_APPLE);
        Storage.storeFruit(ORANGE, AMOUNT_ORANGE);
        Storage.storeFruit("banana", 5);
        List<String> storage = Storage.getStorage()
                .entrySet()
                .stream()
                .map(t -> t.getKey() + FruitPackerImpl.COMMA + t.getValue())
                .collect(Collectors.toList());
        storage.add(0, FIRST_STRING);

        assertEquals(storage, reporter.report());
    }

    @Test
    public void processDailyReport_differentFruits() {
        Storage.clearStorage();
        Storage.storeFruit("pear", 8);
        List<String> storage = Storage.getStorage()
                .entrySet()
                .stream()
                .map(t -> t.getKey() + FruitPackerImpl.COMMA + t.getValue())
                .collect(Collectors.toList());
        storage.add(0, FIRST_STRING);

        assertEquals(storage, reporter.report());
    }

    @Test
    public void processDailyReport_largeAmounts() {
        Storage.clearStorage();
        Storage.storeFruit(APPLE, 1000);
        List<String> storage = Storage.getStorage()
                .entrySet()
                .stream()
                .map(t -> t.getKey() + FruitPackerImpl.COMMA + t.getValue())
                .collect(Collectors.toList());
        storage.add(0, FIRST_STRING);

        assertEquals(storage, reporter.report());
    }
}
