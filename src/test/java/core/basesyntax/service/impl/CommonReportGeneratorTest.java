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
    private static final String COMMA = ",";
    private static final String NAME = "test";
    private final Storage storage = Storage.getInstance();
    private final ReportGenerator reportGenerator = new CommonReportGenerator();

    @AfterEach
    void setUp() {
        storage.getStorage().clear();
    }

    @Test
    void generate_storageNotEmpty_ok() {
        Product testProduct = new Fruit(NAME, 0);
        storage.getStorage().put(NAME, testProduct);
        String expectedType = testProduct.getClass().getSimpleName();
        List<Report> expected = List.of(new Report(
                 expectedType + "Report.csv",
                expectedType.toLowerCase() + COMMA + "quantity" + System.lineSeparator(),
                NAME + COMMA + testProduct.getCount() + System.lineSeparator()));
        List<Report> actual = reportGenerator.generate();
        assertEquals(expected, actual);
    }

    @Test
    void generate_storageEmpty_notOk() {
        Exception actual =
                assertThrows(RuntimeException.class, reportGenerator::generate);
        assertEquals("DB is empty", actual.getMessage());
    }
}
