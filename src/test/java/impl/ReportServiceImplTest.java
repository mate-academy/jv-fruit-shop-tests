package impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import service.TransactionProcessor;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReportServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String HEAD = "fruit,quantity" + LINE_SEPARATOR;
    private static final String PATH = "src/test/java/resources/TestInputData.csv";
    private FileReaderImpl fileReader;
    private ParseDataServiceImpl parse;
    private ReportServiceImpl reportService;
    private TransactionProcessor transaction;

    @BeforeAll
    void setUp() {
        fileReader = new FileReaderImpl();
        parse = new ParseDataServiceImpl();
        reportService = new ReportServiceImpl();
        transaction = new TransactionProcessor();

        List<String> dataFromFile = fileReader.readFile(PATH);
        List<FruitTransaction> fruitsList = parse.parseData(dataFromFile);
        transaction.transactionFruits(fruitsList);
    }

    @Test
    void reportServiceTest_createReport_ok() {
        String actualReport = reportService.createReport();
        String expectedReport = HEAD
                + "banana,210"
                + LINE_SEPARATOR
                + "apple,90"
                + LINE_SEPARATOR;
        assertEquals(expectedReport, actualReport);
    }
}
