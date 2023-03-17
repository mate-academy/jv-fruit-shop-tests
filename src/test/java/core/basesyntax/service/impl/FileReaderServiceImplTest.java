package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private static List<String> actual;
    private static final String EMPTY_FILE =
            "/Users/MacBook/Desktop/base_1/jv-fruit-shop-tests/src/test/resources/empty.csv";
    private static final String NOT_EXISTING_FILE =
            "/Users/MacBook/Desktop/base_1/jv-fruit-shop-tests/src/main/resources/cucaracha.csv";
    private static final String NORMAL_FILE =
            "src/test/resources/input.csv";
    private static File file;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        file = new File(NORMAL_FILE);
    }

    @After
    public void cleanStorage() {
        Storage.getFruits().clear();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_inputFileIsEmpty_notOk() {
        actual = fileReaderService.readFromFile(EMPTY_FILE);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_inputFileNull_notOk() {
        actual = fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_inputFileNotExist_notOk() {
        actual = fileReaderService.readFromFile(NOT_EXISTING_FILE);
    }

    @Test
    public void readFromFile_inputFileExist_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read this file " + e);
        }
        actual = fileReaderService.readFromFile(NORMAL_FILE);
        assertEquals(actual, expected);
    }
}
