package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ReportGenerator;
import core.basesyntax.dao.impl.ReportGeneratorImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {

    @Test
    void generate_correctReport_Ok() {
        FruitStorage fruitStorage = new FruitStorage();
        ReportGenerator reportGenerator = new ReportGeneratorImpl(fruitStorage);

        fruitStorage.addFruit("apple", 100);
        fruitStorage.addFruit("banana", 50);
        fruitStorage.addFruit("peach", 10);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "peach,10" + System.lineSeparator();

        String actualResult = reportGenerator.getReport();

        assertEquals(expectedReport, actualResult);
    }
}
