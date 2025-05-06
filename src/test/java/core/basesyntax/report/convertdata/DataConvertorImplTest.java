package core.basesyntax.report.convertdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitOperation;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConvertorImplTest {
    private DataConvertor dataConvertor;

    @BeforeEach
    void setUp() {
        dataConvertor = new DataConvertorImpl();
    }

    @Test
    void testConvertValidLines() {
        List<String> input = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,10",
                "s,banana,20"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(input);

        assertEquals(2, result.size());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(FruitOperation.Operation.BALANCE, result.get(0).getOperation());

        assertEquals("banana", result.get(1).getFruit());
        assertEquals(20, result.get(1).getQuantity());
        assertEquals(FruitOperation.Operation.SUPPLY, result.get(1).getOperation());
    }

    @Test
    void testSkipInvalidColumnCount() {
        List<String> input = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple",
                "s,banana,20"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(input);

        assertEquals(1, result.size());
        assertEquals("banana", result.get(0).getFruit());
    }

    @Test
    void testSkipEmptyFruitName() {
        List<String> input = Arrays.asList(
                "operation,fruit,quantity",
                "b,,10",
                "s,banana,20"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(input);

        assertEquals(1, result.size());
        assertEquals("banana", result.get(0).getFruit());
    }

    @Test
    void testSkipInvalidOperation() {
        List<String> input = Arrays.asList(
                "operation,fruit,quantity",
                "x,apple,10",
                "s,banana,20"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(input);

        assertEquals(1, result.size());
        assertEquals("banana", result.get(0).getFruit());
    }

    @Test
    void testSkipNegativeQuantity() {
        List<String> input = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,-5",
                "s,banana,20"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(input);

        assertEquals(1, result.size());
        assertEquals("banana", result.get(0).getFruit());
    }
}
