package service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import data.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String firstFruit = "banana";
    private static final String secondFruit = "apple";
    private static final String separator = ",";
    private static final String HEADLINE = "fruit,quantity";
    private static final int firstQuantity = 1;
    private static final int secondQuantity = 2;
    private static final String expectedReport = HEADLINE + System.lineSeparator()
            + firstFruit + separator + firstQuantity + System.lineSeparator()
            + secondFruit + separator + secondQuantity;
    private static ReportGenerator generator;

    @BeforeAll
    static void createStorage() {
        generator = new ReportGeneratorImpl();
    }

    @Test
    void getValidReport_ok() {
        Storage.updateFruitStorage(firstFruit, firstQuantity);
        Storage.updateFruitStorage(secondFruit, secondQuantity);
        String actual = generator.generateStorageReport();

        assertEquals(expectedReport, actual);
    }

    @Test
    void getEmptyReport_ok() {
        Storage.removeFromFruitStorage(firstFruit);
        Storage.removeFromFruitStorage(secondFruit);
        String actual = generator.generateStorageReport();

        assertEquals(HEADLINE, actual);
    }
}
