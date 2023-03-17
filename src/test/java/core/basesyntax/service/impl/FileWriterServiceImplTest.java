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
    private static final String HEAD = "fruit,quantity";
    private static final String INVALID_FILE_PATH = "G://unreal_folder.csv";
    private static final String VALID_FILE_PATH =
            "/Users/MacBook/Desktop/base_1/jv-fruit-shop-tests/src/test/resources/new.csv";
    private static String REPORT = HEAD;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFile_notOk() {
        fileWriterService.writeToFile(INVALID_FILE_PATH, "");
    }

    @Test
    public void writeToFile_validFata_Ok() {
        File file = new File(VALID_FILE_PATH);
        fileWriterService.writeToFile(REPORT, VALID_FILE_PATH);
        try {
            String expected = Files.readAllLines(file.toPath()).get(0);
            assertEquals(REPORT, expected);
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
