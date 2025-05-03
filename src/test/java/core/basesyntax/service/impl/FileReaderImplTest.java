package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_PATH = "src\\test\\resources\\fileReaderTestFile.csv";
    private static final String INVALID_PATH = "src/test/,resources/fileReaderTestFileOne.csv";
    private static final String NOT_EXISTED_FILE_PATH = "src\\test\\resources\\notExists.csv";
    private static final String EMPTY_FILE_PATH
            = "src\\test\\resources\\emptyFileForFileReaderTest.csv";
    private static FileReader reader;
    private static List<String> records;

    @BeforeClass
    public static void setUp() {
        reader = new FileReaderImpl();
        records = List.of("fruit,quantity", "mellon,32", "peach,190", "grapes,99");
    }

    @Test
    public void readLines_validFile_Ok() {
        Path path = new File(VALID_PATH).toPath();
        try {
            Files.write(path, records);
        } catch (IOException e) {
            throw new RuntimeException("Cant write to file " + VALID_PATH, e);
        }
        List<String> expected = records;
        List<String> actual = reader.readLines(VALID_PATH);
        assertEquals("Should return data which equals that was written",
                expected, actual);
    }

    @Test
    public void readLine_emptyFile_Ok() {
        File file = new File(EMPTY_FILE_PATH);
        try {
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cant create empty file in test method", e);
        }
        int expected = 0;
        int actual = reader.readLines(EMPTY_FILE_PATH).size();
        assertEquals("Should return empty list using empty file",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readLines_invalidPath_NotOk() {
        reader.readLines(INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readLines_notExistedFile_NotOk() {
        reader.readLines(NOT_EXISTED_FILE_PATH);
    }

    @AfterClass
    public static void cleanUp() {
        String[] paths = new String[]{VALID_PATH, INVALID_PATH, EMPTY_FILE_PATH};
        for (String filePath : paths) {
            new File(filePath).delete();
        }
    }
}
