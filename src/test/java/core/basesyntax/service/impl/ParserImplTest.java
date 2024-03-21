package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private ParserImpl parser;

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parseValidRecords_ok() {
        List<String> records = Arrays.asList("b,apple,10",
                "s,banana,20", "p,orange,30", "r,pear,40");
        List<FruitTransaction> transactions = parser.parse(records);
        assertEquals(4, transactions.size(),
                "Should parse all records correctly.");
    }

    @Test
    void parseInvalidFormat_notOk() {
        List<String> records = List.of("b,apple");
        Exception exception = assertThrows(RuntimeException.class, () -> parser.parse(records));
        assertTrue(exception.getMessage().contains("Can't parse data"));
    }

    @Test
    void parseInvalidOperationCode_notOk() {
        List<String> records = List.of("x,apple,10");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(records));
        assertTrue(exception.getMessage().contains("Invalid code"));
    }

    @Test
    void parseInvalidAmountFormat_notOk() {
        List<String> records = List.of("b,apple,xyz");
        Exception exception = assertThrows(NumberFormatException.class,
                () -> parser.parse(records));
        assertNotNull(exception, "Should throw NumberFormatException for invalid integer format.");
    }

    @Test
    void parseEmptyList_notOk() {
        List<String> records = List.of();
        List<FruitTransaction> transactions = parser.parse(records);
        assertTrue(transactions.isEmpty(), "Should return an empty list for empty input.");
    }
}
