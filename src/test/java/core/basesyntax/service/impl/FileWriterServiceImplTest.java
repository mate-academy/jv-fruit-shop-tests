package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_PATH_NAME = "src/main/resources/DailyReport.csv";
    private static final String DATA_TO_WRITE = "Test file writer";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void init() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validFilePath_Ok() throws IOException {
        Files.writeString(Path.of(VALID_PATH_NAME), DATA_TO_WRITE);
        String actual = Files.readString(Path.of(VALID_PATH_NAME));
        assertEquals(String.format("Should return: %s \n but was: %s",
                DATA_TO_WRITE, actual), DATA_TO_WRITE, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullFilePath_notOk() {
        fileWriterService.writeToFile(null, DATA_TO_WRITE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notValidFilePath_notOk() {
        fileWriterService.writeToFile("invalidPath", DATA_TO_WRITE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportIsNull_notOk() {
        fileWriterService.writeToFile(VALID_PATH_NAME, null);
    }
}
