package core.basesyntax.service.fruittransactionparser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
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

    @Before
    public void setUp() throws Exception {
        Storage.storage.clear();
        this.rowList = new ArrayList<>();
        this.fruitTransactions = new ArrayList<>();
        this.fruitParser = new FruitTransactionParserImpl();
        rowList.add(TITLE);
    }

    @Test
    public void parseToFruitTransactions_ValidString_Ok() {
        String fruitLine = "s,banana,100";
        rowList.add(fruitLine);
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",100);
        fruitTransactions.add(fruit);
        List<FruitTransaction> expected = fruitTransactions;
        List<FruitTransaction> actual = fruitParser.parseToFruitTransactions(rowList);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseToFruitTransactions_NullString_NotOk() {
        String firstNull = null;
        rowList.add(firstNull);
        fruitParser.parseToFruitTransactions(rowList);
    }

    @Test(expected = RuntimeException.class)
    public void parseToFruitTransactions_notValidOperation_NotOk() {
        String fruitLine = "super,banana,100";
        rowList.add(fruitLine);
        fruitParser.parseToFruitTransactions(rowList);
    }

    @Test
    public void parseToFruitTransactions_MaxQuantity_Ok() {
        String fruitLine = "b,apple," + Integer.MAX_VALUE;
        rowList.add(fruitLine);
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",Integer.MAX_VALUE);
        fruitTransactions.add(fruit);
        List<FruitTransaction> expected = fruitTransactions;
        List<FruitTransaction> actual = fruitParser.parseToFruitTransactions(rowList);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseToFruitTransactions_NullFruitName_NotOk() {
        String fruit = "b," + null + "," + 10;
        rowList.add(fruit);
        fruitParser.parseToFruitTransactions(rowList);
    }

    @Test(expected = RuntimeException.class)
    public void parseToFruitTransactions_emptyNameFruit_NotOk() {
        String fruit = "b," + "" + "," + 10;
        rowList.add(fruit);
        fruitParser.parseToFruitTransactions(rowList);
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
