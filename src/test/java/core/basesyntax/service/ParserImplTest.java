package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_listWithWrongOperationType_notOk() {
        List<String> data = List.of("w,banana,20", "q,apple,100");
        assertThrows(NoSuchElementException.class, () ->
                parser.parse(data));
    }

    @Test
    public void parse_listWithWrongQuantityType_notOk() {
        List<String> data = List.of("b,banana,twenty", "b,apple,hundred");
        assertThrows(NumberFormatException.class, () ->
                parser.parse(data));
    }

    @Test
    public void parse_emptyList_ok() {
        List<String> data = new ArrayList<>();
        List<FruitTransaction> fruitTransactions = parser.parse(data);
        assertTrue(fruitTransactions.isEmpty());
    }

    @Test
    public void parse_wrongFormatList_notOk() {
        List<String> data = List.of("b-banana-20", "b-apple-100");
        assertThrows(NoSuchElementException.class, () ->
                parser.parse(data));
    }

    @Test
    public void parse_validList_ok() {
        List<String> data = List.of("b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13");
        List<FruitTransaction> actual = parser.parse(data);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 13));
        assertEquals(actual, expected);
    }
}
