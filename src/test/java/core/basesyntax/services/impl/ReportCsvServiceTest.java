package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.ReportCsvService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCsvServiceTest {
    private static Storage fruitDB;
    private static ReportCsvService reportCsvServiceTest;

    @BeforeAll
    static void createReportService() {
        fruitDB = new Storage();
        reportCsvServiceTest = new ReportCsvServiceImpl(fruitDB);
    }

    @BeforeEach
    void cleanStorage() {
        fruitDB.clean();
    }

    @Test
    void reportCsv_nullStorage_notOk() {
        ReportCsvService reportCsvServiceTemp = new ReportCsvServiceImpl(null);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTemp.createReport());
    }

    @Test
    void reportCsv_nullFruit_notOk() {
        fruitDB.add(null, 20);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void reportCsv_emptyFruit_notOk() {
        fruitDB.add("", 20);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void reportCsv_nullValue_notOk() {
        fruitDB.add("banana", null);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void reportCsv_correctTestOneFruit_ok() {
        fruitDB.add("banana", 20);
        String[] expected = {"fruit,quantity", "banana,20"};
        String[] actual = reportCsvServiceTest.createReport();
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void reportCsv_correctTestThreeFruits_ok() {
        fruitDB.add("banana", 20);
        fruitDB.add("apple", 50);
        fruitDB.add("orange", 90);
        String[] expected =
                {"fruit,quantity",
                        "banana,20",
                        "orange,90",
                        "apple,50"};
        String[] actual = reportCsvServiceTest.createReport();
        assertTrue(Arrays.deepEquals(expected, actual));
    }
}
