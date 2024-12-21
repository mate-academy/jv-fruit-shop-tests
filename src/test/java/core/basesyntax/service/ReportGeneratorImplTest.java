package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 200);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void getReport_validData_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator() + "banana,100"
                + System.lineSeparator() + "apple,200" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();

        System.out.println("Expected Report: [" + expectedReport + "]");
        System.out.println("Actual Report: [" + actualReport + "]");

        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getReport_emptyStorage_ok() {
        Storage.fruits.clear();
        String expectedReport = "fruit,quantity\n";
        String actualReport = reportGenerator.getReport().replace(System.lineSeparator(), "\n");

        System.out.println("Expected Report: [" + expectedReport + "]");
        System.out.println("Actual Report: [" + actualReport + "]");

        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getReport_singleItem_ok() {
        Storage.fruits.clear();
        Storage.fruits.put("mango", 50);
        String expectedReport = "fruit,quantity\nmango,50\n";
        String actualReport = reportGenerator.getReport().replace(System.lineSeparator(), "\n");

        System.out.println("Expected Report: [" + expectedReport + "]");
        System.out.println("Actual Report: [" + actualReport + "]");

        assertEquals(expectedReport, actualReport);
    }

}
