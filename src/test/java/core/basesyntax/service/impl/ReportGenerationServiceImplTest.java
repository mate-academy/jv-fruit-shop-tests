package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoCsvImpl;
import core.basesyntax.service.ReportGenerationService;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGenerationServiceImplTest {
    private static final String REPORT_FIRST_LINE = "fruit,quantity";
    private static FruitShopDao fruitShopDao;
    private static ReportGenerationService reportGenerationService;

    @BeforeAll
    static void beforeAll() {
        fruitShopDao = new FruitShopDaoCsvImpl();
        reportGenerationService = new ReportGenerationServiceImpl(fruitShopDao);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitQuantities.clear();
    }

    @Test
    void generateReport_emptyStorage_ok() {
        List<String> expected = new ArrayList<>();
        expected.add(REPORT_FIRST_LINE);
        List<String> actual = reportGenerationService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_validStorage_ok() {
        fillStorage();
        List<String> expected = new ArrayList<>();
        expected.add(REPORT_FIRST_LINE);
        expected.add("banana,33");
        expected.add("apple,0");
        expected.add("peach,133");
        List<String> actual = reportGenerationService.generateReport();
        assertEquals(expected, actual);
    }

    private void fillStorage() {
        Storage.fruitQuantities.put("banana", 33);
        Storage.fruitQuantities.put("apple", 0);
        Storage.fruitQuantities.put("peach", 133);
    }
}
