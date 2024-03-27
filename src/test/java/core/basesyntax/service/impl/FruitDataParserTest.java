package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitDataParser;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.strategy.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDataParserTest {
    private DataParser<FruitTransactionDto> dataParser;

    @BeforeEach
    void setUp() {
        dataParser = new FruitDataParser();
    }

    @Test
    void parse_ValidData_Ok() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,50",
                "s,orange,30"
        );

        List<FruitTransactionDto> expected = List.of(
                new FruitTransactionDto(Operation.BALANCE, "banana", 20),
                new FruitTransactionDto(Operation.PURCHASE, "apple", 50),
                new FruitTransactionDto(Operation.SUPPLY, "orange", 30)
        );

        List<FruitTransactionDto> actual = dataParser.parse(rawData);
        assertEquals(expected, actual);
    }

    @Test
    void parse_InvalidData_notOk() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "b,banana",
                "p,apple,50",
                "s,orange,30"
        );

        assertThrows(IllegalArgumentException.class, () ->
                dataParser.parse(rawData));
    }

    @Test
    void parse_EmptyData_ReturnsEmptyList() {
        List<String> rawData = List.of();

        List<FruitTransactionDto> result = dataParser.parse(rawData);
        assertEquals(0, result.size());
    }

    @Test
    void parse_NullData_notOk() {
        assertThrows(NullPointerException.class, () ->
                dataParser.parse(null));
    }
}
