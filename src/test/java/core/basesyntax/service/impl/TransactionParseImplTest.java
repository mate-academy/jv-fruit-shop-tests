package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParseImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String TYPE = "b";
    private static final String FIRST_FRUIT = "banana";
    private static final int FIRST_QUANTITY = 20;
    private static final String SECOND_FRUIT = "apple";
    private static final int SECOND_QUANTITY = 100;
    private TransactionParser transactionParser;
    private List<String> dailyTransactionList;

    @Before
    public void setUp() {
        transactionParser = new TransactionParseImpl();
        dailyTransactionList = new ArrayList<>();
    }

    @Test
    public void parse_Ok() {
        dailyTransactionList.add(HEADER);
        dailyTransactionList.add(buildTransactionString(TYPE, FIRST_FRUIT, FIRST_QUANTITY));
        dailyTransactionList.add(buildTransactionString(TYPE, SECOND_FRUIT, SECOND_QUANTITY));

        List<FruitTransaction> parseList = transactionParser.parse(dailyTransactionList);
        assertEquals(2, parseList.size());

        FruitTransaction firstTransaction = parseList.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation());
        assertEquals(FIRST_FRUIT, firstTransaction.getFruit());
        assertEquals(FIRST_QUANTITY, firstTransaction.getQuantity());

        FruitTransaction secondTransaction = parseList.get(1);
        assertEquals(FruitTransaction.Operation.BALANCE, secondTransaction.getOperation());
        assertEquals(SECOND_FRUIT, secondTransaction.getFruit());
        assertEquals(SECOND_QUANTITY, secondTransaction.getQuantity());
    }

    @Test
    public void parse_EmptyData_Ok() {
        List<FruitTransaction> parseList = transactionParser.parse(dailyTransactionList);
        assertEquals(0, parseList.size());
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidData_NotOk() {
        dailyTransactionList.add(HEADER);
        dailyTransactionList.add("Kitekat,1lb");
        transactionParser.parse(dailyTransactionList);
    }

    private String buildTransactionString(String type, String fruit, int quantity) {
        return type + "," + fruit + "," + quantity;
    }
}
