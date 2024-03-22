package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Product;
import core.basesyntax.model.Report;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonReportGeneratorTest {
    private static final String SEPARATOR = ",";
    private static final String NAME = "test";
    private static final String REPORT_CSV = "Report.csv";
    private static final String QUANTITY = "quantity";
    private static final String DB_IS_EMPTY = "DB is empty";
    private final Storage storage = Storage.getInstance();
    private final ReportGenerator reportGenerator = new CommonReportGenerator();

    @BeforeEach
    void setUp() {
        storage.getStorage().clear();
    }

    @Test
    void generate_storageNotEmpty_ok() {
        Product testProduct = new Fruit(NAME, 0);
        storage.getStorage().put(NAME, testProduct);

        List<Report> actual = reportGenerator.generate();

        String expectedType = testProduct.getClass().getSimpleName();
        List<Report> expected = List.of(new Report(
                 expectedType + REPORT_CSV,
                expectedType.toLowerCase() + SEPARATOR + QUANTITY + System.lineSeparator(),
                NAME + SEPARATOR + testProduct.getCount() + System.lineSeparator()));
        assertEquals(expected, actual);
    }

    @Test
    void generate_storageEmpty_notOk() {
        Exception actual =
                assertThrows(RuntimeException.class, reportGenerator::generate);

        assertEquals(DB_IS_EMPTY, actual.getMessage());
    }
}
