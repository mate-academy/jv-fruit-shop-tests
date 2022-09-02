package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserTransactionsServiceImpl;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class ParserTransactionServiceImplTest {
    public static final String SEPARATOR = ",";
    public static final int OPERATION_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;
    private List<String> correctListForParsing;
    private List<String> notCorrectListForParsing;
    private List<FruitTransaction> expectedList;

    @BeforeClass
    public static void beforeClass() throws Exception {
    }

    @Before
    public void setUp() {
        correctListForParsing = new ArrayList<>();
        correctListForParsing.add("type,fruit,quantity");
        correctListForParsing.add("b,banana,20");
        correctListForParsing.add("b,apple,100");
        correctListForParsing.add("s,banana,100");
        correctListForParsing.add("p,banana,13");
        correctListForParsing.add("r,apple,10");

        expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20));
        expectedList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100));
        expectedList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 100));
        expectedList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 13));
        expectedList.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, new Fruit("apple"), 10));
    }

    @Test
    public void parse_correctData_ok() {
        List<FruitTransaction> actualList = new ParserTransactionsServiceImpl().parse(correctListForParsing);
        Assert.assertEquals(actualList, expectedList);
    }

    @After
    public void tearDown() throws Exception {
        correctListForParsing.clear();

    }
}
