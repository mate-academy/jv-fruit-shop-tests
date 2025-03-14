package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

    private FruitStorage apple = new FruitStorage("apple", 10);
    private FruitStorage banana = new FruitStorage("banana", 20);

    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void reportGenerator_shouldGenerateListOfString() {

        List<FruitStorage> input = List.of(apple, banana);
        List<String> expected = List.of("fruit,quantity","apple,10", "banana,20");

        assertEquals(expected, reportGenerator.report(input));
    }

    @Test
    void reportGenerator_shouldReturnListWithoutFruits() {

        List<FruitStorage> input = List.of();
        List<String> expected = List.of("fruit,quantity");

        assertEquals(expected, reportGenerator.report(input));
    }
}
