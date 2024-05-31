package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitModel;
import core.basesyntax.service.ReportWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private ReportWriter reportWriter;

    @BeforeEach
    void setUp() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    void createReport_OK() {
        Storage.fruitStorage.put(FruitModel.BANANA, 20);
        Storage.fruitStorage.put(FruitModel.APPLE, 50);
        Storage.fruitStorage.put(FruitModel.PINEAPPLE, 30);
        String report = reportWriter.createReport();
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("apple,50"));
        assertTrue(report.contains("pineapple,30"));
        String[] lines = report.split(System.lineSeparator());
        assertEquals("fruit,quantity", lines[0]);
    }

    @Test
    void createReport_emptyStorage_OK() {
        String report = reportWriter.createReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,0" + System.lineSeparator()
                + "apple,0" + System.lineSeparator()
                + "lime,0" + System.lineSeparator()
                + "peach,0" + System.lineSeparator()
                + "pineapple,0";
        assertEquals(expectedReport, report);
    }

    @Test
    void createReport_oneFruitInStorage_returnValidReport() {
        Storage.fruitStorage.put(FruitModel.PEACH, 34);
        String report = reportWriter.createReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,0" + System.lineSeparator()
                + "apple,0" + System.lineSeparator()
                + "lime,0" + System.lineSeparator()
                + "peach,34" + System.lineSeparator()
                + "pineapple,0";
        assertEquals(expectedReport, report);
    }

    @Test
    void createReport_fewFruitsInStorage_returnValidReport() {
        Storage.fruitStorage.put(FruitModel.APPLE, 5);
        Storage.fruitStorage.put(FruitModel.PINEAPPLE, 53);
        String firstReport = reportWriter.createReport();
        String firstExpectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,0" + System.lineSeparator()
                + "apple,5" + System.lineSeparator()
                + "lime,0" + System.lineSeparator()
                + "peach,0" + System.lineSeparator()
                + "pineapple,53";
        assertEquals(firstExpectedReport, firstReport);
        Storage.fruitStorage.put(FruitModel.BANANA, 28);
        String secondReport = reportWriter.createReport();
        String secondExpectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,28" + System.lineSeparator()
                + "apple,5" + System.lineSeparator()
                + "lime,0" + System.lineSeparator()
                + "peach,0" + System.lineSeparator()
                + "pineapple,53";
        assertEquals(secondExpectedReport, secondReport);
    }

    @AfterEach
    void cleanUp() {
        Storage.fruitStorage.clear();
    }
}
