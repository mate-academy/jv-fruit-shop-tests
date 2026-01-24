package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvProcessorTest {

    private static CsvProcessor processor;

    @BeforeAll
    static void init() {
        processor = new CsvProcessor(new OperationStrategyImpl());
    }

    @Test
    void process_ShouldReturnCorrectInventory_WhenInputIsValid() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,10",
                "p,banana,5"
        );

        Map<String, Integer> result = processor.process(input);

        assertEquals(25, result.get("banana"));
    }

    @Test
    void process_ShouldThrowException_WhenLineIsInvalid() {
        List<String> input = List.of("b,banana");

        assertThrows(RuntimeException.class,
                () -> processor.process(input));
    }

    @Test
    void process_ShouldThrowException_WhenQuantityIsNotANumber() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );

        assertThrows(RuntimeException.class,
                () -> processor.process(input));
    }
}
