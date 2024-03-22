package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dto.FruitTransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDataParserTest {

    public static final List EMPTY_LIST = new ArrayList(0);
    public static final List<String> VALID_lIST = List.of("    type,fruit,quantity",
            "   b,banana,20",
            "    b,apple,100",
            "    s,banana,100");
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
    void parseValidListOk() {
        var expected = EXPECTED_LIST;
        var actual = fruitDataParser.parse(VALID_lIST);
        assertEquals(expected, actual, "Method should return" + expected + "but was: " + actual);
    }

    @Test
    void parseEmptyRawDataReturnsEmptyListOk() {
        var expected = EMPTY_LIST;
        var actual = fruitDataParser.parse(EMPTY_LIST);
        assertEquals(expected, actual, "Method should return empty list but was: " + actual);
    }
}
