package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.FruitReportGeneratorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitReportGeneratorImplTest {
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    private final ReportGenerator reportGenerator = new FruitReportGeneratorImpl();

    @Test
    void generateReport_validInput_correctOutput() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(APPLE, 10));
        fruits.add(new Fruit(BANANA, 20));

        String expectedReport = "fruit,quantity"
                + System.lineSeparator() + "apple,10"
                + System.lineSeparator() + "banana,20"
                + System.lineSeparator();
        String actualReport = reportGenerator.generateReport(fruits);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void generateReport_emptyInput_throwsException() {
        List<Fruit> fruits = new ArrayList<>();
        String expectedMessage = "Report cannot be generated, because nothing happens";
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reportGenerator.generateReport(fruits));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
