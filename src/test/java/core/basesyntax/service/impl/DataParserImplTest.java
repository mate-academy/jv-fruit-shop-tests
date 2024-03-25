package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParser;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataParserImplTest {
    private static DataParser dataParser;

    @BeforeAll
    static void beforeAll() {
        dataParser = new DataParserImpl();
    }

    @Test
    void processAll_validInput_ok() {
        List<String> data = List.of("header", "b,banana,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20));
        List<FruitTransaction> actual = dataParser.parseAll(data);
        assertEquals(expected, actual);
    }

    @Test
    void processAll_validLongInput_ok() {
        List<String> data = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50)
        );

        List<FruitTransaction> actual = dataParser.parseAll(data);
        assertEquals(expected, actual);
    }

    @Test
    void processAll_wrongInput_notOk() {
        List<String> data = List.of("header", "wrong");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                dataParser.parseAll(data));
        assertEquals("Can't process data: wrong", exception.getMessage());
    }

    @Test
    void processAll_emptyInput_notOk() {
        assertThrows(RuntimeException.class, () ->
                dataParser.parseAll(null));
    }

    @Test
    void processAll_negativeQuantity_notOk() {
        List<String> data = List.of("header", "b,banana,-5");
        NumberFormatException exception = assertThrows(NumberFormatException.class, () ->
                dataParser.parseAll(data));
        assertEquals("Quantity can't be less than 0, but was: -5", exception.getMessage());
    }
}
