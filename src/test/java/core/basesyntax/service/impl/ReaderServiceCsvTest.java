package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceCsvTest {
    private static final String FILENAME = "test.csv";
    private static final String EXPECTED_CONTENTS =
            "type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator()
                    + "s,banana,100";
    private static final String NONEXISTENT_FILE_CSV = "nonexistent.csv";
    private static final String TEMP_DIR_NAME = "ReaderServiceCsvTest";
    private static final String SETUP_EXCEPTION = "Exception during reader test setup";
    private static Path TMP_DIR;
    private static File TEST_FILE;

    private static ReaderServiceCsv readerService;

    @BeforeClass
    public static void setup() {
        readerService = new ReaderServiceCsv();
        try {
            TMP_DIR = Files.createTempDirectory(TEMP_DIR_NAME);
            TEST_FILE = Paths.get(TMP_DIR.toString(), FILENAME).toFile();
            Files.write(TEST_FILE.toPath(), EXPECTED_CONTENTS.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(SETUP_EXCEPTION);
        }
    }

    @AfterClass
    public static void cleanup() throws IOException {
        Files.deleteIfExists(TEST_FILE.toPath());
        Files.deleteIfExists(TMP_DIR);
    }

    @Test
    public void readLines_withValidFile_ok() {
        List<String> expectedLines = Arrays.asList(EXPECTED_CONTENTS.split(System.lineSeparator()));
        List<String> actualLines = readerService.readLines(TEST_FILE);
        Assert.assertEquals(expectedLines, actualLines);
    }

    @Test(expected = RuntimeException.class)
    public void readLines_withNonExistentFile_notOk() {
        File nonExistentFile = new File(NONEXISTENT_FILE_CSV);
        readerService.readLines(nonExistentFile);
    }

    @Test(expected = NullPointerException.class)
    public void readLines_nullFile_notOk() {
        readerService.readLines(null);
    }
}
