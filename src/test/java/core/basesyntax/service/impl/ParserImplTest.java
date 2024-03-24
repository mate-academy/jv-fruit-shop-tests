package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operations;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private ParserImpl parser;
    private static final String BALANCE_APPLE_40 = "b,apple,40";
    private static final String BALANCE_BANANA_20 = "b,banana,20";
    private static final String PURCHASE_ORANGE_30 = "p,orange,30";
    private static final String RETURN_PEAR_40 = "r,pear,40";

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parseValidNumberOfRecords_ok() {
        List<String> records = Arrays.asList(BALANCE_APPLE_40,
                BALANCE_BANANA_20, PURCHASE_ORANGE_30, RETURN_PEAR_40);
        List<FruitTransaction> transactions = parser.parse(records);
        assertEquals(4, transactions.size(),
                "Should parse all records correctly.");
    }

    @Test
    void parseValidRecords_ok() {
        List<String> records = Arrays.asList(BALANCE_APPLE_40,
                BALANCE_BANANA_20, PURCHASE_ORANGE_30, RETURN_PEAR_40);
        List<FruitTransaction> transactions = parser.parse(records);
        assertEquals(Operations.BALANCE, transactions.get(0).operation(),
                "First operation mismatch.");
        assertEquals("apple", transactions.get(0).product(),
                "First product mismatch.");
        assertEquals(40, transactions.get(0).amount(),
                "First amount mismatch.");
        assertEquals(Operations.BALANCE, transactions.get(1).operation(),
                "Second operation mismatch.");
        assertEquals("banana", transactions.get(1).product(),
                "Second product mismatch.");
        assertEquals(20, transactions.get(1).amount(),
                "Second amount mismatch.");
        assertEquals(Operations.PURCHASE, transactions.get(2).operation(),
                "Third operation mismatch.");
        assertEquals("orange", transactions.get(2).product(),
                "Third product mismatch.");
        assertEquals(30, transactions.get(2).amount(),
                "Third amount mismatch.");
        assertEquals(Operations.RETURN, transactions.get(3).operation(),
                "Fourth operation mismatch.");
        assertEquals("pear", transactions.get(3).product(),
                "Fourth product mismatch.");
        assertEquals(40, transactions.get(3).amount(),
                "Fourth amount mismatch.");
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
