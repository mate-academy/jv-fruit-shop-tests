package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_PATH = "src\\main\\resources\\fileReaderTestFile.csv";
    private static final String INVALID_PATH = "src/main/,resources/fileReaderTestFileOne.csv";
    private static final String NOT_EXISTED_FILE_PATH = "src\\main\\resources\\notExists.csv";
    private static final String EMPTY_FILE_PATH
            = "src\\main\\resources\\emptyFileForFileReaderTest.csv";
    private static FileReaderImpl reader;
    private static List<String> records;

    @BeforeClass
    public static void setUp() {
        System.out.println("beforeClass");
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
        assertEquals("Should return data which equals that was written",
                records, reader.readLines(VALID_PATH));

    }

    @Test
    public void readLine_emptyFile_Ok() {
        File file = new File(EMPTY_FILE_PATH);
        try {
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cant create empty file in test method", e);
        }
        assertEquals("Should return empty list using empty file",
                0, reader.readLines(EMPTY_FILE_PATH).size());

    }

    @Test(expected = RuntimeException.class)
    public void readLines_invalidPath_NotOk() {
        reader.readLines(INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readLines_notExistedFile_NotOk() {
        File file = new File(NOT_EXISTED_FILE_PATH);
        if (file.exists()) {
            fail("This file should not exist for correct work of this test. File="
                    + NOT_EXISTED_FILE_PATH);
        }
        reader.readLines(NOT_EXISTED_FILE_PATH);

    }

    @AfterClass
    public static void removeFile() {
        String[] paths = new String[]{VALID_PATH, INVALID_PATH, EMPTY_FILE_PATH};
        for (String filePath : paths) {
            new File(filePath).delete();
        }
    }
}
