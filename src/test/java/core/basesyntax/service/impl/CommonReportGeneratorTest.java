package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Product;
import core.basesyntax.model.Report;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class CommonReportGeneratorTest {
    private static final ReportGenerator REPORT_GENERATOR = new CommonReportGenerator();
    private static final Storage STORAGE = Storage.getInstance();
    private static final String COMMA = ",";
    private static final String NAME = "test";
    private static final String QUANTITY = "quantity";
    private static final String REPORT_CSV = "Report.csv";
    private static final String DB_IS_EMPTY = "DB is empty";

    @Test
    void generate_storageNotEmpty_ok() {
        Product testProduct = new Fruit(NAME, 0);
        STORAGE.getStorage().put(NAME, testProduct);
        String expectedType = testProduct.getClass().getSimpleName();
        List<Report> expected = List.of(new Report(
                 expectedType + REPORT_CSV,
                expectedType.toLowerCase() + COMMA + QUANTITY + System.lineSeparator(),
                NAME + COMMA + testProduct.getCount() + System.lineSeparator()));
        List<Report> actual = REPORT_GENERATOR.generate();
        assertEquals(expected, actual);
    }

    @Test
    void generate_storageEmpty_notOk() {
        Exception actual =
                assertThrows(RuntimeException.class, REPORT_GENERATOR::generate);
        assertEquals(DB_IS_EMPTY, actual.getMessage());
    }

    @AfterEach
    void setUp() {
        STORAGE.getStorage().clear();
    }
}
