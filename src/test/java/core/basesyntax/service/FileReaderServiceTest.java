package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.File;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String testTransactionFilePath = "src/main/resources/transaction.csv";

    @Test
    public void fileToRead_isSourceFileExist_OK() {
        File sourceFile = new File(testTransactionFilePath);
        assertTrue(sourceFile.exists());
    }

    @Test
    public void listString_isNotEmpty_OK() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> testList = fileReaderService.readFromFile(testTransactionFilePath);
        assertFalse(testList.isEmpty());
    }
}
