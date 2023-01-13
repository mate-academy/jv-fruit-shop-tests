package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/report.csv";
    private static final String INVALID_FILE_PATH = "not.today";
    private static final String DATA_FOR_TEST_FILE = "test sentence";
    private static WriteFileService writeFileService;

    @BeforeClass
    public static void beforeClass() {
        writeFileService = new WriteFileServiceImpl();
    }

    @Test
    public void writeToFile_writeWithValidPath_ok() {
        writeFileService.writeToFile(VALID_FILE_PATH, DATA_FOR_TEST_FILE);
        String actual = readFromTestFile();
        assertEquals(String.format("Should write \"%s\" to file, but was -> %s",
                DATA_FOR_TEST_FILE, actual), DATA_FOR_TEST_FILE, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeWithInvalidPath_notOk() {
        writeFileService.writeToFile(INVALID_FILE_PATH, DATA_FOR_TEST_FILE);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_pathIsNull_notOk() {
        writeFileService.writeToFile(null, DATA_FOR_TEST_FILE);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeDataIsNull_notOk() {
        writeFileService.writeToFile(VALID_FILE_PATH, null);
    }

    private String readFromTestFile() {
        try {
            return Files.readString(Path.of(WriteFileServiceImplTest.VALID_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Can`t get data from file %s",
                            WriteFileServiceImplTest.VALID_FILE_PATH));
        }
    }
}
