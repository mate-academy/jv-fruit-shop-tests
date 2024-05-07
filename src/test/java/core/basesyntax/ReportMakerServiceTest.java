package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.servise.ReportMakerService;
import core.basesyntax.servise.impl.ReportMakerServiceImpl;
import core.basesyntax.testclasses.DaoStorageForTest;
import core.basesyntax.testclasses.StorageForTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportMakerServiceTest {
    static final String LINE_SEPARATOR = System.lineSeparator();
    static final String FRUIT = "banana";
    static final int QUANTITY = 100;
    static final String TITLE = "fruit,quantity" + LINE_SEPARATOR;
    static final String SEPARATOR = ",";
    private static ReportMakerService reportMakerService;

    @BeforeAll
    public static void setUp() {
        reportMakerService = new ReportMakerServiceImpl(new DaoStorageForTest());
    }

    @Test
    public void reportMakerService_daoStorageNull_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new ReportMakerServiceImpl(null));
    }

    @Test
    public void reportMakerService_generateReport_Ok() {
        StorageForTest.getTestStorage().put(FRUIT, QUANTITY);
        String actual = reportMakerService.generateReport();
        assertAll("Test failed! Report format isn`t correct",
                () -> assertTrue(actual.startsWith(TITLE)),
                () -> assertTrue(actual.contains(FRUIT + SEPARATOR + QUANTITY))
        );
    }
}
