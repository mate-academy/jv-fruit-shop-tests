package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String INVALID_FILE_PATH = "G://unreal_folder.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/new.csv";
    private static String REPORT = "fruit,quantity";

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFile_notOk() {
        fileWriterService.writeToFile(INVALID_FILE_PATH, "");
    }

    @Test
    public void writeToFile_validFata_Ok() throws IOException {
        File file = new File(VALID_FILE_PATH);
        fileWriterService.writeToFile(REPORT, VALID_FILE_PATH);
        String expected = Files.readAllLines(file.toPath()).get(0);
        assertEquals(REPORT, expected);
        file.delete();
    }
}
