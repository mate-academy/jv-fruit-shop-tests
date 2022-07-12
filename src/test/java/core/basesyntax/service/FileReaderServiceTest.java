package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String FILE_HEADER = "type,fruit,quantity";
    private static final String testTransactionFilePath = "src/main/resources/transaction.csv";
    private static final List<String> testList = new ArrayList<>();
    private static FileReaderService fileReaderService;
    private static List<String> testListFromFile;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testList.add(FILE_HEADER);
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        testList.add("s,banana,100");
        testList.add("p,banana,13");
        testList.add("r,apple,10");
        testList.add("p,apple,20");
        testList.add("p,banana,5");
        testList.add("s,banana,50");
    }

    @Before
    public void setUp() throws Exception {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_OK() {
        testListFromFile = fileReaderService.readFromFile(testTransactionFilePath);
        assertEquals(testList, testListFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderService_wrongFilePath_NotOK() {
        String wrongFilePath = "C:/Windows/Temp/io.sys";
        fileReaderService.readFromFile(wrongFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderService_nullFilePath_NotOK() {
        fileReaderService.readFromFile(null);
    }
}
