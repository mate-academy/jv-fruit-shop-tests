package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String testTransactionFileHeader = "type,fruit,quantity";
    private static final String testTransactionFilePath = "src/main/resources/transaction.csv";
    private static List<String> testList = new ArrayList<>();
    private static List<String> testListFromFile;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        testListFromFile = fileReaderService.readFromFile(testTransactionFilePath);
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        testList.add("s,banana,100");
        testList.add("p,banana,13");
        testList.add("r,apple,10");
        testList.add("p,apple,20");
        testList.add("p,banana,5");
        testList.add("s,banana,50");

    }

    @Test
    public void readFromFile_equals_OK() {
        assertTrue(testList.equals(testListFromFile));
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderService_wrongFilePath_NotOK() {
        String wrongFilePath = "C:/Windows/Temp/io.sys";
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        fileReaderService.readFromFile(wrongFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderService_nullFilePath_NotOK() {
        String wrongFilePath = null;
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        fileReaderService.readFromFile(wrongFilePath);
    }
}
