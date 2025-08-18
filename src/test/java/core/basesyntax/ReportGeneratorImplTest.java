package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void getReport_singleFruit_ok() {
        Storage.getFruitStorage().put("orange", 75);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "orange,75" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_withData_ok() {
        Storage.getFruitStorage().put("apple", 100);
        Storage.getFruitStorage().put("banana", 50);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_emptyStorage_returnsHeaderOnly() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    public void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
