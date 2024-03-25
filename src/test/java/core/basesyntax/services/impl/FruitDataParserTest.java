package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDataParserTest {

    public static final List EMPTY_LIST = new ArrayList();
    public static final List<String> VALID_LIST = List.of("    type,fruit,quantity",
            "   b,banana,20",
            "    b,apple,100",
            "    s,banana,100");
    public static final List<String> INVALID_LIST = List.of("    type;fruit;quantity",
            "   b;banana;20",
            "    b;apple;100",
            "    s;banana;100");
    public static final List<FruitTransactionDto> EXPECTED_LIST = List.of(
            new FruitTransactionDto("b", "banana", 20),
            new FruitTransactionDto("b", "apple", 100),
            new FruitTransactionDto("s", "banana", 100)
    );
    private FruitDataParser fruitDataParser;

    @BeforeEach
    void setUp() {
        fruitDataParser = new FruitDataParser();
    }

    @Test
    void parse_ValidList_ReturnsExpectedResult_Ok() {
        var actual = fruitDataParser.parse(VALID_LIST);
        assertEquals(EXPECTED_LIST, actual);
    }

    @Test
    void parse_EmptyRawData_ReturnsEmptyList_Ok() {
        var actual = fruitDataParser.parse(EMPTY_LIST);
        assertEquals(EMPTY_LIST, actual);
    }

    @Test
    void parse_InvalidSeparator_ThrowsException_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitDataParser.parse(INVALID_LIST),
                "Expected IllegalArgumentException for invalid separator");
    }
}
