package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void generateReport_validReportFormat_ok() {
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        Storage.updateFruitQuantity(apple, 10);
        Storage.updateFruitQuantity(banana, 5);
        Set<String> expectedLines = new HashSet<>(Arrays.asList(
                "fruit,quantity",
                "apple,10",
                "banana,5"
        ));
        String actualReport = reportGenerator.generateReport();
        Set<String> actualLines = new HashSet<>(Arrays.asList(actualReport
                .split(System.lineSeparator())));
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void generateReport_oneFruitInReport_ok() {
        Fruit orange = new Fruit("orange");
        Storage.updateFruitQuantity(orange, 7);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "orange,7";
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport);
    }
}
