package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.impl.CsvTransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserTest {
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new CsvTransactionParserImpl();
    }

    @Test
    public void parse_validOutput_Ok() {
        List<String> data = List.of("type,fruit,amount","b,apple,90","b,banana,30");
        List<FruitOperation> actual = transactionParser.parseDataFile(data);
        List<FruitOperation> expected = new ArrayList<>();
        expected.add(new FruitOperation(FruitOperation.Operation.BALANCE,"apple",90));
        expected.add(new FruitOperation(FruitOperation.Operation.BALANCE, "banana",30));
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parse_nullData_NotOk() {
        transactionParser.parseDataFile(null);
    }
}
