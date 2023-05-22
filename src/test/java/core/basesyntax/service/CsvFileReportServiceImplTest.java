package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import core.basesyntax.testservice.ConfigReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvFileReportServiceImplTest {
    private static CsvFileReportService csvFileReportService;
    private static ConfigReader configReader;
    private static Path fileCopyPath;
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 10;
    private static final String EXPECTED_REPORT = "fruit,quantity\nbanana,10";
    private static final String EXPECTED_FIRST_LINE = "fruit,quantity";

    @BeforeAll
    public static void init() {
        csvFileReportService = new CsvFileReportServiceImpl();
        configReader = new ConfigReader();
    }

    @BeforeEach
    public void createFileCopy() throws IOException {
        fileCopyPath = Files.createTempDirectory("temp")
                .resolve("file_copy.csv");
        Files.copy(Path.of(configReader.getValueByKey("file.read.from")),
                fileCopyPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterEach
    public void restoreFileCopy() throws IOException {
        Path filePath = Path.of(configReader.getValueByKey("file.read.from"));
        Files.copy(fileCopyPath, filePath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(fileCopyPath);
    }

    @Test
    public void createReportWithOneProduct_isOk() {
        Map<String, Integer> products = new HashMap<>();
        products.put(FRUIT, QUANTITY);
        String actual = csvFileReportService.createReport(products);
        Assertions.assertEquals(EXPECTED_REPORT, actual,
                "Incorrect report for a single product");
    }

    @Test
    public void createReportWithMultipleProducts_isOk() {
        Map<String, Integer> products = new HashMap<>();
        products.put("banana", 20);
        products.put("apple", 100);
        String expected = "fruit,quantity\nbanana,20\napple,100";
        String actual = csvFileReportService.createReport(products);
        Assertions.assertEquals(expected, actual,
                "Incorrect report for multiple products");
    }

    @Test
    public void createReportWithEmptyProductList_isOk() {
        Map<String, Integer> products = new HashMap<>();
        String actual = csvFileReportService.createReport(products);
        Assertions.assertEquals(EXPECTED_FIRST_LINE, actual,
                "Incorrect report for empty product list");
    }

    @Test
    public void createReportWithNullArgument_isNotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileReportService.createReport(null),
                "NullPointerException for null argument expected");
    }

}
