package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserServiceImpl parserService;
    private List<FruitTransaction> fruitTransactionList;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
        fruitTransactionList = new ArrayList<>();
    }

    @Test
    void parse_validString_ok() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple,1");
        transactionStrings.add("s,banana,2");
        transactionStrings.add("p,apple,11");
        transactionStrings.add("r,banana,22");
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
        List<FruitTransaction> parsedTransactions = parserService.parseData(new ArrayList<>());
        assertTrue(parsedTransactions.isEmpty());
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

    @AfterEach
    void tearDown() {
        fruitTransactionList.clear();
    }
}
