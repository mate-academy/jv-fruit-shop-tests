package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.FruitReportGeneratorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new FruitReportGeneratorImpl();

    @Test
    void generateReport_validInput_correctOutput() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("apple", 10));
        fruits.add(new Fruit("banana", 20));

        String expectedReport = "fruit,quantity"
                + System.lineSeparator() + "apple,10"
                + System.lineSeparator() + "banana,20"
                + System.lineSeparator();
        String actualReport = reportGenerator.generateReport(fruits);
        assertEquals(expectedReport, actualReport);
    }
}
