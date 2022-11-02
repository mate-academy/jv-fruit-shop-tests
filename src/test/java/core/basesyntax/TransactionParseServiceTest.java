package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParseService;
import core.basesyntax.service.impl.TransactionParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParseServiceTest {
    private static final String TEST_DATA = "b,banana,10";
    private static final String TEST_FRUIT_NAME = "banana";
    private static final int TEST_QUANTITY = 10;
    private final TransactionParseService transactionParse = new TransactionParseServiceImpl();

    @Test
    public void parseInputData_correctData_Ok() {
        List<String> testData = new ArrayList<>();
        testData.add(TEST_DATA);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT_NAME, TEST_QUANTITY));
        List<FruitTransaction> actual = transactionParse.parseInputData(testData);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void createReport_NullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParse.parseInputData(null);
        });
    }
}
