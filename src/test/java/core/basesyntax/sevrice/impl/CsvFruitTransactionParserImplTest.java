package core.basesyntax.sevrice.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.sevrice.CsvFruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFruitTransactionParserImplTest {
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final Fruit APPLE = new Fruit("apple");
    private static final FruitTransaction APPLE_BALANCE_OPERATION
            = new FruitTransaction(FruitTransaction.Operation.BALANCE,
            APPLE, 100);
    private static final String FIRST_STRING = "this,must,deleted";
    private final List<FruitTransaction> transactions = new ArrayList<>();
    private final List<String> testList = new ArrayList<>();
    private final CsvFruitTransactionParser csvFruitTransactionParser
            = new CsvFruitTransactionParserImpl();

    @Before
    public void setUp() {
        testList.add(FIRST_STRING);
        transactions.add(APPLE_BALANCE_OPERATION);
    }

    @Test
    public void parse_correctInfo_ok() {
        String appleBalance = "b,apple,100";
        testList.add(appleBalance);
        List<FruitTransaction> actual = csvFruitTransactionParser.parse(testList);
        List<FruitTransaction> expected = transactions;
        Assert.assertEquals(expected.get(FIRST_ELEMENT_INDEX), actual.get(FIRST_ELEMENT_INDEX));
    }

    @Test(expected = RuntimeException.class)
    public void parse_usedIncorrectInfo_notOk() {
        String incorrectInfo = "Balance,pig,-100500";
        testList.add(incorrectInfo);
        csvFruitTransactionParser.parse(testList);
    }

    @Test(expected = RuntimeException.class)
    public void parser_nullList_notOk() {
        List<String> nullList = null;
        csvFruitTransactionParser.parse(nullList);
    }
}
