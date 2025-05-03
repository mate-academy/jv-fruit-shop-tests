package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserTransactionImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserTransactionImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserTransactionImpl();
    }

    @Test
    void parseTransaction_emptyList_Ok() {
        List<String> list = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> parserService.parseRecords(list));
    }

    @Test
    void parseTransaction_null_Ok() {
        assertThrows(RuntimeException.class,
                () -> parserService.parseRecords(null));
    }

    @Test
    void parseTransaction_notCorrectOperation_notOk() {
        List<String> list = new ArrayList<>();
        list.add("a,apple,25");
        list.add("c,banana,15");
        assertThrows(RuntimeException.class,
                () -> parserService.parseRecords(list));
    }

    @Test
    void parseRecords_notCorrectFruit_notOk() {
        List<String> list = new ArrayList<>();
        list.add("s-pear-12");
        list.add("p-plum-7");
        assertThrows(RuntimeException.class,
                () -> parserService.parseRecords(list));
    }

    @Test
    void parseRecords_notCorrectQuantity_notOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,one");
        list.add("r,apple,two");
        assertThrows(NumberFormatException.class,
                () -> parserService.parseRecords(list));
    }

    @Test
    void parseTransaction_Ok() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("r,apple,25");
        list.add("s,banana,15");
        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 25));
        expectedTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 15));
        List<FruitTransaction> actualTransactions = parserService.parseRecords(list);
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }
}
