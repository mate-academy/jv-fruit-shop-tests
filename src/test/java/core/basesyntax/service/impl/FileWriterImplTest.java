package core.basesyntax.service.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String VALID_PATH = "src\\test\\resources\\fileWriterTestFile.csv";
    private static final String INVALID_PATH = "src/test/,resources/fileWriterTestFileOne.csv";
    private static File resultFile;
    private static FileWriter writer;
    private static List<String> records;

    @BeforeClass
    public static void setUp() {
        writer = new FileWriterImpl();
        records = List.of("fruit,quantity", "orange,56", "lemon,69", "apple,78");
        resultFile = new File(VALID_PATH);
    }

    @Test
    public void writeLines_validPath_Ok() {
        writer.writeLines(VALID_PATH, records);
        List<String> actual = readLines(VALID_PATH);
        List<String> expected = records;
        assertEquals("Should be the same records that was written", expected, actual);
    }

    @Test
    public void writeLines_validPathSeveralTimes_Ok() {
        for (int i = 0; i < 10; i++) {
            writer.writeLines(VALID_PATH, records);
        }
        List<String> expected = records;
        List<String> actual = readLines(VALID_PATH);
        assertEquals("Should be the same records that was written at first call",
                expected, actual);
    }

    @Test
    public void writeLines_emptyList_Ok() {
        writer.writeLines(VALID_PATH, new ArrayList<>());
        int expected = 0;
        int actual = readLines(VALID_PATH).size();
        assertEquals("Should have no data in destination file",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeLines_shouldThrowIfInvalidPath_NotOk() {
        writer.writeLines(INVALID_PATH, records);
    }

    @After
    public void cleanUp() {
        if (resultFile.exists()) {
            resultFile.delete();
        }
    }

    private List<String> readLines(String path) {
        try {
            return Files.readAllLines(new File(path).toPath());
        } catch (IOException ignored) {
            throw new RuntimeException("Cannot read the file " + path);
        }
    }
}
