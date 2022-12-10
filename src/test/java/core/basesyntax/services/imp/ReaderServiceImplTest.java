package core.basesyntax.services.imp;

import core.basesyntax.services.FileReadService;
import org.junit.Test;

public class ReaderServiceImplTest {
    private final FileReadService fileReadService = new ReaderServiceImpl();

    @Test(expected = RuntimeException.class)
    public void file_is_null() {
        String test = null;
        fileReadService.readFromFile(test);
    }

    @Test(expected = RuntimeException.class)
    public void file_not_exist_NotOK() {
        String test = "FileNotExist";
        fileReadService.readFromFile(test);
    }

    @Test
    public void read_correctFile_OK() {
        fileReadService.readFromFile("src/test/resources/ReadTestFile.csv");
    }

}
