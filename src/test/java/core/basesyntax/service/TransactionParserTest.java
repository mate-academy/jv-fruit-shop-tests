package core.basesyntax.service;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.impl.CsvTransactionParserImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionParserTest {
    private static final String INPUT_TEST_PATH = "src/test/resources/inputTest3.csv";
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new CsvTransactionParserImpl();
    }

//    @Test
//    public void parse_validOutput_Ok() {
//        FileReaderService fileReaderService = new FileReaderServiceImpl();
//        List<String> data = fileReaderService.readFromFile(INPUT_TEST_PATH);
//        List<FruitOperation> actual = transactionParser.parseDataFile(data);
//        List<FruitOperation> expected = new ArrayList<>();
//        expected.add(new FruitOperation(FruitOperation.Operation.BALANCE,
//                "apple",90));
//        expected.add(new FruitOperation(FruitOperation.Operation.BALANCE,
//                "banana",30));
//        assertEquals(expected, actual);
//    }

    @Test (expected = NullPointerException.class)
    public void parse_nullData_NotOk() {
        transactionParser.parseDataFile(null);
    }
}
