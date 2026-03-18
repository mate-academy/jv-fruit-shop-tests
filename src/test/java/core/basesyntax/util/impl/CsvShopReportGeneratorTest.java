package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.util.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvShopReportGeneratorTest {
    private static final String TEST_REPORT_HEADER = "fruit,quantity";

    private Storage shopStorage;
    private final ReportGenerator reportGenerator = new CsvShopReportGenerator();

    @BeforeEach
    void setUp() {
        shopStorage = new ShopStorage();
    }

    @Test
    void generate_storageIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> reportGenerator.generate(null));
    }

    @Test
    void generate_validCase_returnsRightReport_ok() {
        shopStorage.update("banana", 152);
        shopStorage.update("apple", 90);

        String actual = reportGenerator.generate(shopStorage);

        assertTrue(actual.startsWith(TEST_REPORT_HEADER));
        assertTrue(actual.contains("banana,152"));
        assertTrue(actual.contains("apple,90"));
    }

    @Test
    void generate_emptyStorage_returnsOnlyHeader_ok() {
        String actual = reportGenerator.generate(shopStorage);

        assertEquals(TEST_REPORT_HEADER, actual,
                "generate() does not return a header when storage is empty");
    }
}
