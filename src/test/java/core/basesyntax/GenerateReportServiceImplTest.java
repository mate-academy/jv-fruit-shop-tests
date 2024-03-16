package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.service.GenerateReportService;
import core.basesyntax.service.impl.GenerateReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenerateReportServiceImplTest {
    private GenerateReportService generateReportService;

    @BeforeEach
    void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        generateReportService = new GenerateReportServiceImpl(fruitDao);
    }

    @Test
    void generateReport_correctData_ok() {
        Storage.storageFruits.put("banana", 10);
        String expected = "fruit,quantity\nbanana,10";
        assertEquals(expected, generateReportService.generateReport());
    }

    @AfterEach
    void tearDown() {
        Storage.storageFruits.clear();
    }
}
