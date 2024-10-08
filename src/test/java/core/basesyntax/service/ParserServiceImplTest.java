package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserServiceImpl parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_validLines_ok() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,100",
                "s,banana,50"
        );

        List<FruitTransaction> result = parserService.parse(lines);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        Assertions.assertEquals("apple", result.get(0).getFruit());
        Assertions.assertEquals(100, result.get(0).getQuantity());

        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        Assertions.assertEquals("banana", result.get(1).getFruit());
        Assertions.assertEquals(50, result.get(1).getQuantity());
    }

    @Test
    void parse_invalidLineFormat_skipped() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple",
                "s,banana,50"
        );

        List<FruitTransaction> result = parserService.parse(lines);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("banana", result.get(0).getFruit());
        Assertions.assertEquals(50, result.get(0).getQuantity());
    }

    @Test
    void parse_invalidQuantity_skipped() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,abc",
                "s,banana,50"
        );

        List<FruitTransaction> result = parserService.parse(lines);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("banana", result.get(0).getFruit());
        Assertions.assertEquals(50, result.get(0).getQuantity());
    }

    @Test
    void parse_invalidOperation_skipped() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "x,apple,100",
                "s,banana,50"
        );

        List<FruitTransaction> result = parserService.parse(lines);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("banana", result.get(0).getFruit());
        Assertions.assertEquals(50, result.get(0).getQuantity());
    }

    @Test
    void parse_emptyList_returnsEmptyList() {
        List<String> lines = new ArrayList<>();

        List<FruitTransaction> result = parserService.parse(lines);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void parse_onlyHeader_returnsEmptyList() {
        List<String> lines = List.of("operation,fruit,quantity");

        List<FruitTransaction> result = parserService.parse(lines);

        Assertions.assertTrue(result.isEmpty());
    }
}
