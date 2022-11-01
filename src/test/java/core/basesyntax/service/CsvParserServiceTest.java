package core.basesyntax.service;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserServiceTest {
    private static CsvParserService<FruitTransaction> csvParserService;
    private List<String> testData;

    @BeforeClass
    public static void beforeClass() {
        csvParserService = new CsvParserServiceImpl();
    }

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        testData.add("b,fruit1,100");
        testData.add("b,fruit2,100");
        testData.add("p,fruit1,20");
    }

    @Test
    public void parse_listOfCorrectTransactionRecords_listOfFruitTransactions_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "fruit1", 100),
                new FruitTransaction(Operation.BALANCE, "fruit2", 100),
                new FruitTransaction(Operation.PURCHASE, "fruit1", 20)
        );
        List<FruitTransaction> actual = csvParserService.parse(testData, false);
        assertEquals("Test failed! Expected list of transactions: "
                + expected + ", but was: " + actual,
                expected, actual);
    }

    @Test
    public void parse_withoutHeaderHasHeaderTrue_FirstRecordSkipped_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "fruit2", 100),
                new FruitTransaction(Operation.PURCHASE, "fruit1", 20)
        );
        List<FruitTransaction> actual = csvParserService.parse(testData, true);
        assertEquals("Test failed! Expected list of transactions: "
                        + expected + ", but was: " + actual,
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_ListOfTransactionRecordsWrongOperation_notOk() {
        testData.add("for_sure_not_supported_operation,fruit1,100");
        csvParserService.parse(testData, false);
    }

    @Test(expected = RuntimeException.class)
    public void parse_listOfTransactionRecordsValueMissing_notOk() {
        testData.add("p,100");
        csvParserService.parse(testData, false);
    }

    @Test(expected = RuntimeException.class)
    public void parse_listOfTransactionRecordsEmptyLine_notOk() {
        testData.add("");
        csvParserService.parse(testData, false);
    }

    @Test(expected = RuntimeException.class)
    public void parse_ListOfTransactionRecordsNonNumericQuantity_notOk() {
        testData.add("p,fruit2,non_numeric_quantity");
        csvParserService.parse(testData, false);
    }

    @Test(expected = RuntimeException.class)
    public void parse_ListOfTransactionRecordsFloatPointQuantity_notOk() {
        testData.add("p,fruit2,20.90");
        csvParserService.parse(testData, false);
    }

    @Test(expected = RuntimeException.class)
    public void parse_Null_notOk() {
        csvParserService.parse(null, false);
    }
}
