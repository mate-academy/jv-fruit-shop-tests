package core.basesyntax.service.impl;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String VALID_PATH = "src\\main\\resources\\fileWriterTestFile.csv";
    private static final String INVALID_PATH = "src/main/,resources/fileWriterTestFileOne.csv";
    private static File resultFile;
    private static FileWriterImpl writer;
    private static List<String> records;

    @BeforeClass
    public static void setUp() {
        writer = new FileWriterImpl();
        records = List.of("fruit,quantity", "orange,56", "lemon,69", "apple,78");
        resultFile = new File(VALID_PATH);
    }

    @Test
    public void writeLines_shouldWriteNormally_Ok() {
        writer.writeLines(VALID_PATH, records);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(resultFile.toPath());
        } catch (IOException e) {
            fail("Cannot read the file " + VALID_PATH);
        }
        assertEquals("Should be the same records that was written", records, lines);
    }

    @Test
    public void writeLines_shouldReplaceData_Ok() {
        for (int i = 0; i < 10; i++) {
            writer.writeLines(VALID_PATH, records);
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(resultFile.toPath());
        } catch (IOException e) {
            fail("Cannot read the file " + VALID_PATH);
        }
        assertEquals("Should be the same records that was written at first call",
                records, lines);
    }

    @Test
    public void writeLines_emptyList_Ok() {
        writer.writeLines(VALID_PATH, new ArrayList<>());
        List<String> lines = null;
        try {
            lines = Files.readAllLines(resultFile.toPath());
        } catch (IOException e) {
            fail("Cannot read the file " + VALID_PATH);
        }
        assertEquals("Should have no data in destination file",
                0, lines.size());
    }

    @Test(expected = RuntimeException.class)
    public void writeLines_shouldThrowIfInvalidPath_NotOk() {
        writer.writeLines(INVALID_PATH, records);
    }

    @After
    public void removeFile() {
        if (resultFile.exists()) {
            resultFile.delete();
        }
    }
}
