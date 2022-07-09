package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String testTransactionFileHeader = "type,fruit,quantity";
    private static final String testTransactionFilePath = "src/main/resources/transaction.csv";
    private static List<String> testList;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        testList = fileReaderService.readFromFile(testTransactionFilePath);
    }

    @Test
    public void readFromFile_listHeaderCorrect_OK() {
        assertTrue(testList.get(0).equals(testTransactionFileHeader));
    }

    @Test(expected = NullPointerException.class)
    public void fileReaderService_wrongFilePath_NotOK() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        fileReaderService.readFromFile(null);
    }
}
