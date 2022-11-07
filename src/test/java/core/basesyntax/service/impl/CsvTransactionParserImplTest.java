package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvTransactionParser;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvTransactionParserImplTest {
    private static final List<String> INVALID_OPERATION_TRANSACTIONS =
            List.of("type,fruit,quantity", "%$@#,banana,20");
    private static final List<String> INVALID_INT_VALUE_TRANSACTIONS = List
            .of("type,fruit,quantity", "b,banana,20word");
    private static final List<String> INVALID_NUMBER_OF_VALUES_TRANSACTIONS =
            List.of("type,fruit,quantity", "b,banana");
    private static final String INVALID_NUMBER_OF_VALUES_EXCEPTION_MESSAGE =
            "Incorrect number of data in row";
    private static final String INVALID_OPERATION_EXCEPTION_MESSAGE =
            "Such operation does not exist";
    private static final String INVALID_INT_VALUE_EXCEPTION_MESSAGE =
            "Wrong input type, int type expected";
    private static CsvTransactionParser<List<FruitTransaction>, List<String>> csvTransactionParser;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        csvTransactionParser = new CsvTransactionParserImpl();
    }

    @Test
    public void csvParse_invalidOperationThrowException_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(INVALID_OPERATION_EXCEPTION_MESSAGE);
        csvTransactionParser.csvParse(INVALID_OPERATION_TRANSACTIONS);
    }

    @Test
    public void csvParse_invalidIntegerValueThrowException_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(INVALID_INT_VALUE_EXCEPTION_MESSAGE);
        csvTransactionParser.csvParse(INVALID_INT_VALUE_TRANSACTIONS);
    }

    @Test
    public void csvParse_invalidNumberOfDataInRowThrowException_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(INVALID_NUMBER_OF_VALUES_EXCEPTION_MESSAGE);
        csvTransactionParser.csvParse(INVALID_NUMBER_OF_VALUES_TRANSACTIONS);
    }

    @Test
    public void csvParse_ActualDataEqualsToExpected_ok() {
        List<FruitTransaction> actualFruitTransactions =
                csvTransactionParser.csvParse(getValidTransactions());
        assertEquals(actualFruitTransactions, getExpectedFruitTransactionList());
    }

    private List<String> getValidTransactions() {
        return List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    private List<FruitTransaction> getExpectedFruitTransactionList() {
        return List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }
}
