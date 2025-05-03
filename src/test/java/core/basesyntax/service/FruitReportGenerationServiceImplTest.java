package core.basesyntax.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.FruitReportGenerationServiceImpl;
import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitReportGenerationServiceImplTest {

    private static final String DIRECTORY_PATH = "src/test/resources";
    private static final String REPORT_FILE_NAME = "report.csv";
    private static ReportGenerationService reportGenerationService;

    @BeforeAll
    static void setUp() {
        FruitShopStorage fruitShopStorage = new FruitShopStorage();
        reportGenerationService = new FruitReportGenerationServiceImpl(fruitShopStorage);
        fruitShopStorage.save(Fruit.APPLE, 10, OperationType.BALANCE);
        fruitShopStorage.save(Fruit.BANANA, 20, OperationType.BALANCE);
    }

    @BeforeEach
    void init() {
        File file = new File(DIRECTORY_PATH + "/" + REPORT_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void generateReport_ValidData_ShouldWriteReport() {
        assertDoesNotThrow(
                () -> reportGenerationService.generateReport(DIRECTORY_PATH, REPORT_FILE_NAME));
    }

    @Test
    void generateReport_InvalidPath_ShouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> reportGenerationService.generateReport("---", "report.csv")
        );
    }

    @Test
    void generateReport_NullPath_NullFileName_ShouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> reportGenerationService.generateReport(null, null)
        );
    }

    @Test
    void generateReport_NullPath_ValidFileName_ShouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> reportGenerationService.generateReport(null, "report.csv")
        );
    }

    @Test
    void generateReport_ValidPath_NullFileName_ShouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> reportGenerationService.generateReport("path", null)
        );
    }
}

