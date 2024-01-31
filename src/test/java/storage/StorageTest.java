package storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import impl.FileReaderImpl;
import impl.ParseDataServiceImpl;
import impl.ReportServiceImpl;
import impl.WriterServiceImpl;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import service.TransactionProcessor;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StorageTest {
    private static FileReaderImpl fileReader;
    private static ParseDataServiceImpl parse;
    private static ReportServiceImpl reportService;
    private static WriterServiceImpl writerService;
    private static TransactionProcessor transaction;
    private static final String INPUT_PATH = "src/test/java/TestsFiles/TestInputData.csv";
    private static final String OUTPUT_PATH = "src/test/java/TestsFiles/TestOutputData.csv";
    private static final int expectedLength = 2;

    @BeforeAll
    void setUp() {
        fileReader = new FileReaderImpl();
        parse = new ParseDataServiceImpl();
        reportService = new ReportServiceImpl();
        writerService = new WriterServiceImpl();
        transaction = new TransactionProcessor();
        List<String> dataFromFile = fileReader.readFile(INPUT_PATH);
        List<FruitTransaction> fruitsList = parse.parseData(dataFromFile);
        transaction.transactionFruits(fruitsList);
        String resultOfWork = reportService.createReport();
        writerService.writeDataToFile(resultOfWork, OUTPUT_PATH);
    }

    @Test
    void storageSizeTest_Ok() {
        int actualLength = Storage.getFruitsStorage().size();
        assertEquals(expectedLength, actualLength,
                "You`r storage size must be "
                        + expectedLength
                        + " but was" + actualLength);
    }

    @Test
    void storageQuantity_ok() {
        int expectedBanana = 210;
        int expectedApple = 90;
        int actualBanana = Storage.getValue("banana");
        int actualApple = Storage.getValue("apple");
        assertEquals(expectedBanana, actualBanana,
                "Quantity of banana must be "
                + expectedBanana + " but was " + actualBanana);
        assertEquals(expectedApple, actualApple,
                "Quantity of apple must be "
                + expectedApple + "but was " + actualApple);
    }
}
