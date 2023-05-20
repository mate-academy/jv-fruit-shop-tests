package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.Parser;
import core.basesyntax.service.serviceimpl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserImplTest {
    public static final List<String> parseList = List.of(
            "type,fruit,quantity", "b,banana,50", "b,apple,100",
            "b,orange,100", "s,banana,150", "s,apple,1000",
            "s,orange,2200", "p,banana,13", "r,orange,2");
    private static Parser parser;
    private static List<FruitTransaction> expected;

    @BeforeAll
    static void beforeAll() {
        expected = new ArrayList<>();
        parser = new ParserImpl();
    }

    @Test
    void parse_null_notOk() {
        Assertions.assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    void parse_Ok() {
        fillTransactionsList(expected);
        List<FruitTransaction> actual = parser.parse(parseList);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void parse_inputEmpty_Ok() {
        List<FruitTransaction> actual = parser.parse(new ArrayList<>());
        assertEquals(expected, actual);
    }

    @Test
    void parse_inputEmpty_NotOk() {
        fillTransactionsList(expected);
        List<FruitTransaction> actual = parser.parse(new ArrayList<>());
        assertNotEquals(expected.size(), actual.size());
    }

    @AfterEach
    void tearDown() {
        expected = new ArrayList<>();
    }

    private void fillTransactionsList(List<FruitTransaction> list) {
        FruitTransaction fruitTransaction1 = new FruitTransaction(
                FruitTransaction.Operation.getCode("b"), "banana", 500);
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.getCode("b"), "apple", 100);
        FruitTransaction fruitTransaction3 = new FruitTransaction(
                FruitTransaction.Operation.getCode("b"), "orange", 100);
        FruitTransaction fruitTransaction4 = new FruitTransaction(
                FruitTransaction.Operation.getCode("s"), "banana", 150);
        FruitTransaction fruitTransaction5 = new FruitTransaction(
                FruitTransaction.Operation.getCode("s"), "apple", 1000);
        FruitTransaction fruitTransaction6 = new FruitTransaction(
                FruitTransaction.Operation.getCode("s"), "orange", 2200);
        FruitTransaction fruitTransaction7 = new FruitTransaction(
                FruitTransaction.Operation.getCode("p"), "banana", 13);
        FruitTransaction fruitTransaction8 = new FruitTransaction(
                FruitTransaction.Operation.getCode("r"), "orange", 2);

        list.add(fruitTransaction1);
        list.add(fruitTransaction2);
        list.add(fruitTransaction3);
        list.add(fruitTransaction4);
        list.add(fruitTransaction5);
        list.add(fruitTransaction6);
        list.add(fruitTransaction7);
        list.add(fruitTransaction8);
    }
}
