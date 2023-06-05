package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FormatTransformerServiceImplTest {
    private static FormatTransformerServiceImpl transformerService;

    @BeforeAll
    static void setUp() {
        transformerService = new FormatTransformerServiceImpl();
    }

    @Test
    void formatData_validData_ok() {
        List<String> content = List.of("b,banana,10","b,apple,100", "s,banana,5", "s,apple,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
        List<FruitTransaction> actual = transformerService.formatData(content);
        assertIterableEquals(expected, actual);
    }
}
