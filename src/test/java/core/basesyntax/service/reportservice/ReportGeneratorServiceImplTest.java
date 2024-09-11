package core.basesyntax.service.reportservice;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorServiceImplTest {
    private static final String SEPARATOR = "\n";
    private static final int NUMBER_OF_LINES = 3;
    private static final Map<String, Integer> storageTest = Map.of(
            "banana", 30,
            "lemon", 50
    );
    private static final String EXPECTED_REPORT = """
            fruit,quantity
            banana,30
            lemon,50
            """;
    private FruitStorageDao storageDao;
    private ReportGeneratorService reportGeneratorService;

    @BeforeEach
    void setUp() {
        storageDao = new FruitStorageDaoImpl();
        storageDao.getAllFruits().putAll(storageTest);
        reportGeneratorService = new ReportGeneratorServiceImpl(storageDao);
    }

    @Test
    void createReport_ok() {
        String actualReport = reportGeneratorService.createReportFromDb();
        for (int i = 0; i < NUMBER_OF_LINES; i++) {
            assertEquals(EXPECTED_REPORT.split(SEPARATOR)[i], actualReport.split(SEPARATOR)[i]);
        }
    }

    @AfterEach
    void tearDown() {
        storageDao.getAllFruits().clear();
    }
}