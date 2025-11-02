package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitParserTest {
    @Test
    void parse_correctData_isOk() {
        FruitTransaction actual = FruitParser.parse("b,banana,20");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Type.BALANCE,
                "banana", 20);

        assertEquals(expected, actual);
    }

    @Test
    void parse_nullData_isNotOk() {
        assertThrows(RuntimeException.class, () -> FruitParser.parse(null));
    }

    @Test
    void parse_emptyData_isNotOk() {
        assertThrows(RuntimeException.class, () -> FruitParser.parse(""));
    }

    @Test
    void parse_invalidData_isNotOk() {
        assertThrows(RuntimeException.class, () -> FruitParser.parse("b,banana,20,unknown"));
    }

    @Test
    void parse_numberInWordFormat_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> FruitParser.parse("b,banana,twenty"));
    }
}
