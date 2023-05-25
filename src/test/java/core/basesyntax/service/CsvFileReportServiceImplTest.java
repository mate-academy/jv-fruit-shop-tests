package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvFileReportServiceImplTest {
    private static CsvFileReportService csvFileReportService;
    private static FruitStorage fruitStorage;
    private static FruitDao fruitDao;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final Integer QUANTITY = 10;
    private static final String EXPECTED_REPORT_ONE_PRODUCT = "fruit,quantity\nbanana,10";
    private static final String EXPECTED_REPORT_MULTIPLE_PRODUCTS =
            "fruit,quantity\nbanana,10\napple,10";
    private static final String EXPECTED_FIRST_LINE = "fruit,quantity";

    @BeforeAll
    public static void init() {
        csvFileReportService = new CsvFileReportServiceImpl();
        fruitStorage = new FruitStorage();
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    public void clearFruitStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void createReport_ReportWithOneProduct_Ok() {
        fruitStorage.fruits.put(BANANA, QUANTITY);
        String report = csvFileReportService.createReport(fruitDao.getAllFruits()).toString();
        Assertions.assertEquals(EXPECTED_REPORT_ONE_PRODUCT, report,
                "Incorrect report for one product");
    }

    @Test
    public void createReport_ReportWithMultipleProducts_Ok() {
        fruitStorage.fruits.put(APPLE, QUANTITY);
        fruitStorage.fruits.put(BANANA, QUANTITY);
        String report = csvFileReportService.createReport(fruitDao.getAllFruits()).toString();
        Assertions.assertEquals(EXPECTED_REPORT_MULTIPLE_PRODUCTS, report,
                "Incorrect report for multiple products");
    }

    @Test
    public void createReport_ReportWithEmptyProductList_Ok() {
        Map<String, Integer> products = new HashMap<>();
        Assertions.assertEquals(EXPECTED_FIRST_LINE,
                csvFileReportService.createReport(products),
                "Incorrect report for empty product list");
    }

    @Test
    public void createReport_NoProducts_NotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileReportService.createReport(null),
                "NullPointerException for no products expected");
    }

    @AfterAll
    public static void clear() {
        FruitStorage.fruits.clear();
    }
}
