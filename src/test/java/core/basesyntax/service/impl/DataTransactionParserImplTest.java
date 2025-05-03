package core.basesyntax.service.impl;

import core.basesyntax.service.DataTransactionParser;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataTransactionParserImplTest {
    private static final String COMMA = ",";
    private static FileReaderServiceImpl fileReaderService;
    private static String fromFile = "src/main/resources/input.csv";
    private static DataTransactionParser dataTransactionParser;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        dataTransactionParser = new DataTransactionParserImpl();
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void parseDataTransaction_validData_ok() {
        List<FruitTransaction> fruitTransactions = dataTransactionParser
                .parseDataTransaction(fileReaderService.read(fromFile));
        int expectedSize = 8;
        int actualSize = fruitTransactions.size();
        Assert.assertEquals("Expected size: " + expectedSize,expectedSize, actualSize);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataTransaction_invalidData_notOk() {
        dataTransactionParser.parseDataTransaction(COMMA);
    }
}
