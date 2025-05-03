package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String REPORT_HEADER = "fruit,quantity";
    private static ReportService reportService;
    private StringJoiner testString;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl(new FruitDaoImpl());
    }

    @BeforeEach
    void setUp() {
        testString = new StringJoiner(System.lineSeparator());
        testString.add(REPORT_HEADER);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void buildReport_RegularCase_Ok() {
        testString.add("apricot,10");
        testString.add("pineapple,100");
        Storage.STORAGE.put(new Fruit("apricot"), 10);
        Storage.STORAGE.put(new Fruit("pineapple"), 100);
        String actual = reportService.buildReport();
        assertEquals(testString.toString(), actual);
    }

    @Test
    void buildReport_EmptyData_Ok() {
        String actual = reportService.buildReport();
        assertEquals(testString.toString(), actual,
                "report must start with " + REPORT_HEADER);
    }
}
