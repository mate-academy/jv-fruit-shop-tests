package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.DataParserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataParserServiceImplTest {
    private DataParserService dataParserService;

    @BeforeEach
    void setUp() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    void parse_validData_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<FruitTransaction> expectedOutput = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100)
        );
        List<FruitTransaction> actualOutput = dataParserService.parse(input);
        assertIterableEquals(expectedOutput, actualOutput);
    }

    @Test
    void parse_singleHeader_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity");
        List<FruitTransaction> actualOutput = dataParserService.parse(input);
        assertTrue(actualOutput.isEmpty());
    }

    @Test
    void parse_invalidQuantity_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataParserService.parse(input));
    }

    @Test
    void parse_invalidData_notOk() {
        List<String> input = List.of(
                "b,banana,20",
                "invalid_row",
                "s,banana,100"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataParserService.parse(input));
    }

    @Test
    void parse_emptyData_notOk() {
        List<String> input = Collections.emptyList();
        List<FruitTransaction> actualOutput = dataParserService.parse(input);
        assertTrue(actualOutput.isEmpty());
    }

    @Test
    void parse_nullData_notOk() {
        List<String> input = null;
        assertThrows(IllegalArgumentException.class,
                () -> dataParserService.parse(input));
    }
}
