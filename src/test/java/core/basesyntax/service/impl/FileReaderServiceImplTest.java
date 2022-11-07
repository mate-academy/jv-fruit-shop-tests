package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.nio.file.Path;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String RESOURCES_PATH = "src/test/resources/";
    private static FileReaderService reader;

    @Test(expected = RuntimeException.class)
    public void read_withNullPath_notOk() {
        reader = new FileReaderServiceImpl(null);
        reader.readFile();
    }

    @Test(expected = RuntimeException.class)
    public void read_UnExistedFile_notOk() {
        reader = new FileReaderServiceImpl(Path.of(RESOURCES_PATH + "unexisting.csv"));
        reader.readFile();
    }

    @Test
    public void read_withExistingFile_Ok() {
        reader = new FileReaderServiceImpl(Path.of(RESOURCES_PATH + "input.csv"));
        reader.readFile();
    }
}
