package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static final int HEADER_INDEX = 0;
    private static ParserService parserService;
    private List<String> testInput;
    private List<FruitTransaction> expected;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testInput = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100")
        );
        expected = List.of(
               new FruitTransaction("b", "banana", 20),
               new FruitTransaction("b", "apple", 100)
        );
    }

    @Test
    void parserService_validInputData_ok() {
        assertIterableEquals(expected, parserService.parseData(testInput));
    }

    @Test
    void parserService_dataWithoutHeader_notOk() {
        testInput.remove(HEADER_INDEX);
        assertNotEquals(expected, parserService.parseData(testInput));
    }

    @Test
    void parserService_invalidData_notOk() {
        testInput.add("k,banana,50");
        assertThrows(IllegalArgumentException.class, () -> parserService.parseData(testInput));
    }
}
