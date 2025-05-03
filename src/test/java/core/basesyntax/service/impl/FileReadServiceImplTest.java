package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.TransactionStorage;
import core.basesyntax.service.file.FileReadService;
import core.basesyntax.service.file.WriteDataToStorageService;
import org.junit.After;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static final WriteDataToStorageService writeDataToStorageService
            = new WriteDataToStorageImpl();
    private FileReadService fileReadService = new FileReadServiceImpl(writeDataToStorageService);
    private TransactionStorage transactionStorage = new TransactionStorage();

    @Test
    public void fileRead_goodFilePath_Ok() {
        String goodFilePath = "src/test/resources/input.csv";
        fileReadService.readDataFromFile(goodFilePath);
        int expected = 8;
        int actual = transactionStorage.getAll().size();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileRead_emptyFilePath_notOk() {
        fileReadService.readDataFromFile("");
        int expected = 0;
        int actual = transactionStorage.getAll().size();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileRead_badFilePath_notOk() {
        String badFilePath = "src/test/resourcesBad/input.csv";
        fileReadService.readDataFromFile(badFilePath);
        int expected = 0;
        int actual = transactionStorage.getAll().size();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileRead_noSuchFile_notOk() {
        String noSuchFile = "src/test/resources/badName.csv";
        fileReadService.readDataFromFile(noSuchFile);
        int expected = 0;
        int actual = transactionStorage.getAll().size();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        transactionStorage.getAll().clear();
    }
}
