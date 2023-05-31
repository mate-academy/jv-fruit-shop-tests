package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionParserImplTest {
    private static final String START_OF_INPUT_DATA = "type,fruit,quantity";
    private static final String BALANCE_CODE = "b";
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 100;
    private static final String COMMA = ",";

    private static TransactionParser transactionParser;
    private static FruitTransaction expectedFruitTransaction;
    private static List<String> fruitTransactions;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImpl();
        expectedFruitTransaction = new FruitTransaction(Operation.BALANCE, FRUIT, QUANTITY);
        fruitTransactions = new ArrayList<>();
    }

    @Before
    public void setUp() {
        fruitTransactions.add(START_OF_INPUT_DATA);
    }

    @Test
    public void parseData_validData_ok() {
        fruitTransactions.add(BALANCE_CODE + COMMA + FRUIT + COMMA + QUANTITY);
        List<FruitTransaction> actualFruitTransactionsList
                = transactionParser.parseData(fruitTransactions);
        FruitTransaction actualFruitTransaction = actualFruitTransactionsList.get(0);
        assertEquals(actualFruitTransaction, expectedFruitTransaction);
    }

    @Test
    public void parseData_NotExistingCode_notOk() {
        fruitTransactions.add("a" + COMMA + FRUIT + COMMA + QUANTITY);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't find operation ");
        transactionParser.parseData(fruitTransactions);
    }

    @Test
    public void parseData_notValidCode_notOk() {
        fruitTransactions.add("1" + COMMA + FRUIT + COMMA + QUANTITY);
        List<FruitTransaction> actualFruitTransactions
                = transactionParser.parseData(fruitTransactions);
        assertTrue(actualFruitTransactions.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void parseData_notValidQuantity_notOk() {
        fruitTransactions.add(BALANCE_CODE + COMMA + FRUIT + COMMA + "1j1");
        transactionParser.parseData(fruitTransactions);
    }

    @Test
    public void parseData_nullData_notOk() {
        fruitTransactions.add(null);
        List<FruitTransaction> actualFruitTransactions
                = transactionParser.parseData(fruitTransactions);
        assertTrue(actualFruitTransactions.isEmpty());
    }

    @Test
    public void parseData_bigNumber_notOk() {
        fruitTransactions.add(BALANCE_CODE + COMMA + FRUIT + COMMA + Integer.MAX_VALUE);
        List<FruitTransaction> actualFruitTransactions
                = transactionParser.parseData(fruitTransactions);
        assertTrue(actualFruitTransactions.isEmpty());
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
    }
}
