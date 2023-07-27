package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.files.FileReaderService;
import core.basesyntax.files.FileReaderServiceImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.operations.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserTest {
    private static ReportService reportService;
    private static FileReaderService fileReaderService;
    private static TransactionParser transactionParser;
    private static final String CORRECT_INPUT_DATA_FILEPATH = "src/test/resources/test.csv";
    private static List<String> dataFromFile;
    private static List<FruitTransaction> expectedList;
    private static final String expectedReport =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,300" + System.lineSeparator()
                    + "apple,125" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
        reportService = new ReportServiceImpl();
        expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple", 60)));
        expectedList.add(new FruitTransaction(Operation.BALANCE, new Fruit("banana", 50)));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)));
        expectedList.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 50)));
        expectedList.add(new FruitTransaction(Operation.RETURN, new Fruit("apple", 30)));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 200)));
        fileReaderService = new FileReaderServiceImpl();
        dataFromFile = fileReaderService.readFromFile(CORRECT_INPUT_DATA_FILEPATH);

        Storage.storage.clear();
        Storage.storage.put("banana", 300);
        Storage.storage.put("apple", 125);
    }

    @Test
    void inputParseData_correctParsingToList_Ok() {
        List<FruitTransaction> actualList = transactionParser.parseTransactions(dataFromFile);
        assertEquals(expectedList, actualList);
    }

    @Test
    void createReport_correctDataOutput_Ok() {
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
