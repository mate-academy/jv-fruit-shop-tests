package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserImplTest {
    private static Parser parser;
    private List<String> data;

    @BeforeAll
    static void beforeAll() {
        parser = new ParserImpl();
    }

    @BeforeEach
    void setUp() {
        data = new ArrayList<>();
    }

    @Test
    public void parse_listWithWrongOperationType_notOk() {
        data.add("w,banana,20");
        data.add("q,apple,100");
        assertThrows(NoSuchElementException.class, () ->
                parser.parse(data));
    }

    @Test
    public void parse_listWithWrongQuantityType_notOk() {
        data.add("b,banana,twenty");
        data.add("b,apple,hundred");
        assertThrows(NumberFormatException.class, () ->
                parser.parse(data));
    }

    @Test
    public void parse_emptyList_ok() {
        List<FruitTransaction> fruitTransactions = parser.parse(data);
        assertTrue(fruitTransactions.isEmpty());
    }

    @Test
    public void parse_wrongFormatList_notOk() {
        data.add("b-banana-20");
        data.add("b-apple-100");
        assertThrows(NoSuchElementException.class, () ->
                parser.parse(data));
    }

    @Test
    public void parse_validList_ok() {
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
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

    @AfterEach
    void tearDown() {
        data.clear();
    }
}
