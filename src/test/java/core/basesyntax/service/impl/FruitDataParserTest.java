package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitDataParser;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.strategy.Operation;
import exception.CustomException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDataParserTest {
    private DataParser<FruitTransactionDto> dataParser;
    private List<String> rawDataOk;
    private List<String> rawDataNotOk;

    @BeforeEach
    void setUp() {
        dataParser = new FruitDataParser();
        rawDataOk = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,50",
                "s,orange,30"
        );
        rawDataNotOk = List.of(
                "type,fruit,quantity",
                "b,banana,b",
                "p,apple,50",
                "s,orange,30"
        );
    }

    @Test
    void parse_ValidData_Ok() {
        List<FruitTransactionDto> expected = List.of(
                new FruitTransactionDto(Operation.BALANCE, "banana", 20),
                new FruitTransactionDto(Operation.PURCHASE, "apple", 50),
                new FruitTransactionDto(Operation.SUPPLY, "orange", 30)
        );

        List<FruitTransactionDto> actual = dataParser.parse(rawDataOk);
        assertEquals(expected, actual);
    }

    @Test
    void parse_InvalidData_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                dataParser.parse(rawDataNotOk));
    }

    @Test
    void parse_EmptyData_ReturnsEmptyList() {
        List<String> rawData = Collections.emptyList();

        List<FruitTransactionDto> result = dataParser.parse(rawData);
        assertEquals(0, result.size());
    }

    @Test
    void parse_NullData_notOk() {
        CustomException customException = assertThrows(CustomException.class, () ->
                dataParser.parse(null));
        assertEquals("Raw data list is null", customException.getMessage());
    }
}
