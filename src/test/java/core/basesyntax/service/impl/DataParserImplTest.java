package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.impl.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    private static DataParser dataParser;
    private static FruitTransaction bananaTransaction;
    private static FruitTransaction appleTransaction;

    @BeforeAll
    static void setUp() {
        dataParser = new DataParserImpl();
        bananaTransaction = new FruitTransaction(Operation.BALANCE, "banana", 20);
        appleTransaction = new FruitTransaction(Operation.BALANCE, "apple", 30);
    }

    @Test
    void getParsedData_nullParsedData_notOk() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> dataParser.getParsedData(nullString),
                "NullPointerException expected to be thrown");
    }

    @Test
    void getParsedData_EmptyParsedData_notOk() {
        String stringEmpty = "";
        assertThrows(InvalidDataException.class, () -> dataParser.getParsedData(stringEmpty),
                "InvalidDataException expected to be thrown");
    }

    @Test
    void getParsedData_validParsedData_Ok() {
        List<FruitTransaction> listForTest =
                new ArrayList<>(List.of(bananaTransaction, appleTransaction));
        String stringForTest = "type,fruit,quantity"
                + System.lineSeparator()
                + "b,banana,20"
                + System.lineSeparator()
                + "b,apple,30";
        assertEquals(listForTest, dataParser.getParsedData(stringForTest));
    }
}
