package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private List<Transaction> transactionList = new ArrayList<>();
    private FruitTransactionParser fruitTransactionParser = new FruitTransactionParserImpl();

    @Before
    public void setUp() {
        transactionList.add(new Transaction(Operation.BALANCE, "apple",10));
        transactionList.add(new Transaction(Operation.PURCHASE, "orange",15));
    }

    @After
    public void tearDown() {
        transactionList.clear();
    }

    @Test
    public void parseList_validData_ok() {
        List<String> transactionsStringList = transactionList
                .stream()
                .map(t -> t.getOperation().getOperation()
                        + FruitTransactionParserImpl.SEPARATE_SYMBOL_FOR_CSV
                        + t.getFruit()
                        + FruitTransactionParserImpl.SEPARATE_SYMBOL_FOR_CSV
                        + t.getAmount())
                .collect(Collectors.toList());
        assertEquals(transactionList,
                fruitTransactionParser.parseList(transactionsStringList));
    }

    @Test
    public void parseList_nullInputList_notOk() {
        assertThrows(RuntimeException.class, () ->
                        fruitTransactionParser.parseList(null),
                "Can't parse NULL list.");
    }
}
