package core.basesyntax.service.fruittransactionparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String TITLE =
            "fruit,quantity" + System.lineSeparator();
    private List<String> rowList;
    private List<FruitTransaction> fruitTransactions;
    private FruitTransactionParser fruitParser;

    public FruitTransactionParserImplTest() {
        this.rowList = new ArrayList<>();
        this.fruitTransactions = new ArrayList<>();
        this.fruitParser = new FruitTransactionParserImpl();
    }

    @Before
    public void setUp() throws Exception {
        rowList.add(TITLE);
    }

    @Test
    public void validString_Ok() {
        String fruitLine = "s,banana,100";
        rowList.add(fruitLine);
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",100);
        fruitTransactions.add(fruit);
        List<FruitTransaction> expected = fruitTransactions;
        List<FruitTransaction> actual = fruitParser.parseToFruitTransactions(rowList);
        assertEquals(expected.toString(),actual.toString());
    }

    @Test
    public void nullString_NotOk() {
        String firstNull = null;
        String secondNull = null;
        rowList.add(firstNull);
        rowList.add(secondNull);
        try {
            fruitParser.parseToFruitTransactions(rowList);
        } catch (NullPointerException e) {
            return;
        }
        fail("You should throw NullPointerException");
    }

    @Test
    public void notValidTransaction_NotOk() {
        String fruitLine = "super,banana,100";
        rowList.add(fruitLine);
        try {
            fruitParser.parseToFruitTransactions(rowList);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException: Activities at the store is not valid,"
                + " add new activity to Enum (Operation)");
    }

    @Test
    public void maxFruitQuantity_Ok() {
        String fruitLine = "b,apple," + Integer.MAX_VALUE;
        rowList.add(fruitLine);
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",Integer.MAX_VALUE);
        fruitTransactions.add(fruit);
        List<FruitTransaction> expected = fruitTransactions;
        List<FruitTransaction> actual = fruitParser.parseToFruitTransactions(rowList);
        assertEquals(expected.toString(),actual.toString());
    }

    @Test
    public void nullNameFruit_NotOk() {
        String fruit = "b," + null + "," + 10;
        rowList.add(fruit);
        try {
            fruitParser.parseToFruitTransactions(rowList);
        } catch (NullPointerException e) {
            return;
        }
        fail("create NullPointerException name of fruit can't be null or empty");
    }

    @Test
    public void emptyNameFruit_NotOk() {
        String fruit = "b," + "" + "," + 10;
        rowList.add(fruit);
        try {
            fruitParser.parseToFruitTransactions(rowList);
        } catch (NullPointerException e) {
            return;
        }
        fail("create NullPointerException name of fruit can't be null or empty");
    }

    @After
    public void tearDown() throws Exception {
        if (!rowList.isEmpty()) {
            rowList.clear();
        }
        if (!fruitTransactions.isEmpty()) {
            fruitTransactions.clear();
        }
    }
}
