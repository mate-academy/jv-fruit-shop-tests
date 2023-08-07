package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.ReportCsvService;
import core.basesyntax.services.impl.ReportCsvServiceImpl;
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
        fruitDB.getStorageFruits().clear();
    }

    @Test
    void reportCsv_isNullStorage_notOk() {
        ReportCsvService reportCsvServiceTemp = new ReportCsvServiceImpl(null);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTemp.createReport());
    }

    @Test
    void reportCsv_isNullFirst_notOk() {
        fruitDB.add(null, 20);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void reportCsv_isEmptyFirst_notOk() {
        fruitDB.add("", 20);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void reportCsv_isNullSecond_notOk() {
        fruitDB.add("banana", null);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void reportCsv_isOneLineCorrect_ok() {
        fruitDB.add("banana", 20);
        String[] expected = {"fruit,quantity", "\r\nbanana,20"};
        String[] actual = reportCsvServiceTest.createReport();
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void reportCsv_isStorageThreeLineCorrect_ok() {
        fruitDB.add("banana", 20);
        fruitDB.add("apple", 50);
        fruitDB.add("orange", 90);
        String[] expected =
                {"fruit,quantity", "\r\nbanana,20", "\r\norange,90", "\r\napple,50"};
        String[] actual = reportCsvServiceTest.createReport();
        assertTrue(Arrays.deepEquals(expected, actual));
    }
}
