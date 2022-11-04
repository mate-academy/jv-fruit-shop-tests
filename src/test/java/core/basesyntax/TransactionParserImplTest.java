package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationValidator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.OperationValidatorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static OperationValidator validator;
    private static List<String> testList;
    private static TransactionParser transactionParser;
    private static FruitTransaction testTransaction;

    private static final String WRONG_TRANSACTION_STRING = "-,banana,20";
    private static final String WRIGHT_TRANSACTION_STRING = "b,banana,20";

    @BeforeClass
    public static void setUp() {
        Map<String, FruitTransaction.Operation> operations = new HashMap<>();
        operations.put("b",FruitTransaction.Operation.BALANCE);
        operations.put("s",FruitTransaction.Operation.SUPPLY);
        operations.put("r",FruitTransaction.Operation.RETURN);
        operations.put("p",FruitTransaction.Operation.PURCHASE);
        validator = new OperationValidatorImpl(operations);
        transactionParser = new TransactionParserImpl(validator);
        testList = new ArrayList<String>();
        testTransaction = new FruitTransaction();
        testTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        testTransaction.setFruit("banana");
        testTransaction.setQuantity(20);
    }

    @Before
    public void beforeAll() {
        testList.clear();
    }

    @Test
    public void validateGetTransactionsFields_Ok() {
        testList.add(WRIGHT_TRANSACTION_STRING);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(testTransaction);
        List<FruitTransaction> actual = transactionParser.parse(testList);
        assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
        assertEquals(expected.get(0).getOperation(), actual.get(0).getOperation());
        assertEquals(expected.get(0).getQuantity(), actual.get(0).getQuantity());
    }

    @Test
    public void validateList_Ok() {
        testList.add(WRIGHT_TRANSACTION_STRING);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(testTransaction);
        List<FruitTransaction> actual = transactionParser.parse(testList);
        assertEquals(expected.toString(),actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void validateNullParameter_NotOk() {
        transactionParser.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void validateWrongTransactionString_NotOk() {
        testList.add(WRONG_TRANSACTION_STRING);
        transactionParser.parse(testList);
    }
}
