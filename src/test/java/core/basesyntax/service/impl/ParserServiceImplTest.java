package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserServiceImpl parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_validString_ok() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple,1");
        transactionStrings.add("s,banana,2");
        transactionStrings.add("p,apple,11");
        transactionStrings.add("r,banana,22");
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1));
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 2));
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 11));
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 22));
        List<FruitTransaction> actual = parserService.parseData(
                transactionStrings);
        assertEquals(fruitTransactionList, actual);
    }

    @Test
    void parse_emptyString_Ok() {
        List<FruitTransaction> actual = parserService.parseData(new ArrayList<>());
        assertTrue(actual.isEmpty());
    }

    @Test
    public void getTransactions_nullList_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.parseData(null));
    }

    @Test
    public void getTransactions_unknownOperation_notOk() {
        List<String> unknownOperation = List.of("type,fruit,quantity", "c,apple,100");
        assertThrows(RuntimeException.class, () -> parserService.parseData(unknownOperation));
    }
}
