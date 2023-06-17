package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.impl.ReportGeneratorServiceImpl;
import core.basesyntax.impl.WriterServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.service.WriterService;
import core.basesyntax.util.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteTests {
    private String valueOutputFile;
    private String pathOutputFile;
    private String pathOutputFileWrong;
    private List<FruitTransaction> listFruitTransaction;
    private FruitShopStorage fruitShopStorageDefault;
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        valueOutputFile = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
        pathOutputFile = "src/test/resources/reportTest.csv";
        pathOutputFileWrong = "src/test/resources/report.csv";
        writerService = new WriterServiceImpl();
        setUpListFruitTransaction();
        setUpFruitShopStorageDefault();
    }

    private void setUpListFruitTransaction() {
        listFruitTransaction = new ArrayList<>();
        listFruitTransaction.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        listFruitTransaction.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
    }

    private void setUpFruitShopStorageDefault() {
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
    }

    @Test
    void writeFile_writeValue_ok() throws IOException {
        WriterServiceImpl writerService = new WriterServiceImpl();
        String expectedValue = valueOutputFile;
        writerService.writeValueToFile(pathOutputFile, expectedValue);
        List<String> lines = Files.readAllLines(Path.of(pathOutputFile));
        String actualValue = String.join(System.lineSeparator(), lines);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void writeFile_writeValue_notOk() {
        writerService.writeValueToFile(pathOutputFile, valueOutputFile);
        try {
            String fileContent = Files.readString(Paths.get(pathOutputFile));
            Assertions.assertEquals(valueOutputFile, fileContent);
        } catch (IOException e) {
            Assertions.fail("Failed to read file: " + pathOutputFile);
        } finally {
            try {
                Files.deleteIfExists(Paths.get(pathOutputFile));
            } catch (IOException e) {
                Assertions.fail("Failed to delete file: " + pathOutputFile);
            }
        }
    }

    @Test
    void generateReport_generateReportFromStorage_ok() {
        ReportGeneratorService reportGeneratorService = new ReportGeneratorServiceImpl();
        String actual = reportGeneratorService.generateReport(fruitShopStorageDefault);
        String expected = valueOutputFile;
        String message = "Expected report: " + expected + ", but got: " + actual;
        Assertions.assertEquals(actual, expected, message);
    }

    @AfterEach
    public void afterEachTest() {
        fruitShopStorageDefault.clear();
    }
}
