package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    private FruitStorage fruitStorageCreator(String fruit, int quantity) {
        return new FruitStorage(fruit, quantity);
    }

    @Test
    void reportGenerator_generateListOfString_Ok() {
        FruitStorage apple = fruitStorageCreator("apple", 10);
        FruitStorage banana = fruitStorageCreator("banana", 20);

        List<FruitStorage> input = List.of(apple, banana);
        List<String> expected = List.of("fruit,quantity","apple,10", "banana,20");

        assertEquals(expected, reportGenerator.report(input));
    }

    @Test
    void reportGenerator_returnListWithoutFruits_Ok() {
        List<FruitStorage> input = List.of();
        List<String> expected = List.of("fruit,quantity");

        assertEquals(expected, reportGenerator.report(input));
    }

    @Test
    void reportGenerator_supportEdgeCases_Ok() {
        FruitStorage apple = fruitStorageCreator("apple", 1000000);
        FruitStorage banana = fruitStorageCreator("banana", -1000000);

        List<FruitStorage> input = List.of(apple, banana);
        List<String> expected = List.of("fruit,quantity", "apple,1000000", "banana,-1000000");

        assertEquals(expected, reportGenerator.report(input));
    }
}
