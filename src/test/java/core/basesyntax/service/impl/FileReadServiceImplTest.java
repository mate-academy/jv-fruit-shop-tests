package core.basesyntax.service.impl;

import core.basesyntax.service.file.FileReadService;
import core.basesyntax.service.file.WriteDataToStorageService;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static final WriteDataToStorageService writeDataToStorageService
            = new WriteDataToStorageImpl();
    private FileReadService fileReadService = new FileReadServiceImpl(writeDataToStorageService);

    @Test
    public void fileRead_goodFilePath_Ok() {
        String goodFilePath = "src/test/resources/input.csv";
        fileReadService.readDataFromFile(goodFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileRead_emptyFilePath_notOk() {
        fileReadService.readDataFromFile("");
    }

    @Test(expected = RuntimeException.class)
    public void fileRead_badFilePath_notOk() {
        String badFilePath = "src/test/resourcesBad/input.csv";
        fileReadService.readDataFromFile(badFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileRead_noSuchFile_notOk() {
        String noSuchFile = "src/test/resources/badName.csv";
        fileReadService.readDataFromFile(noSuchFile);
    }
}
