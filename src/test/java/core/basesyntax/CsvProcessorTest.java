package core.basesyntax;

import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvProcessorTest {

    @Test
    void shouldProcessCsvCorrectly() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,10",
                "p,banana,5"
        );

        OperationStrategy strategy = new OperationStrategyImpl();
        CsvProcessor processor = new CsvProcessor(strategy);

        Map<String, Integer> result = processor.process(input);

        assertEquals(25, result.get("banana"));
    }

    @Test
    void shouldThrowExceptionForInvalidLine() {
        List<String> input = List.of("b,banana");

        CsvProcessor processor =
                new CsvProcessor(new OperationStrategyImpl());

        assertThrows(RuntimeException.class,
                () -> processor.process(input));
    }
}
