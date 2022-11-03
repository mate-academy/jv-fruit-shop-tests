package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParseService;
import core.basesyntax.service.impl.TransactionParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParseServiceTest {
    private static final String TEST_DATA = "b,banana,10";
    private static final String TEST_DATA_2 = "b,apple,10";
    private static final String TEST_FRUIT_NAME_BANANA = "banana";
    private static final String TEST_FRUIT_NAME_APPLE = "apple";
    private static final int TEST_QUANTITY_10 = 10;
    private static final int TEST_QUANTITY_20 = 20;
    private final TransactionParseService transactionParse = new TransactionParseServiceImpl();

    @Test
    public void parseInputData_correctData_Ok() {
        List<String> testData = new ArrayList<>();
        testData.add(TEST_DATA);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT_NAME_BANANA, TEST_QUANTITY_10));
        List<FruitTransaction> actual = transactionParse.parseInputData(testData);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parseInputMultiplyData_correctData_Ok() {
        List<String> testData = new ArrayList<>();
        testData.add(TEST_DATA);
        testData.add(TEST_DATA_2);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT_NAME_BANANA, TEST_QUANTITY_10));
        expected.add(new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT_NAME_APPLE, TEST_QUANTITY_10));
        List<FruitTransaction> actual = transactionParse.parseInputData(testData);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullData_notOk() {
        transactionParse.parseInputData(null);
    }
}
