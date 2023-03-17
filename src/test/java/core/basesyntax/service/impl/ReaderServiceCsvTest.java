package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceCsvTest {
    private static final String FILENAME = "test.csv";
    private static final String EXPECTED_CONTENTS =
            "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100";
    private static final String NONEXISTENT_FILE_CSV = "nonexistent.csv";
    private static final String TEMP_DIR_NAME = "ReaderServiceCsvTest";
    private static Path TMP_DIR;
    private static File TEST_FILE;

    private static ReaderServiceCsv readerService;

    @BeforeAll
    public static void setup() {
        try {
            TMP_DIR = Files.createTempDirectory(TEMP_DIR_NAME);
            TEST_FILE = Paths.get(TMP_DIR.toString(), FILENAME).toFile();
            Files.write(TEST_FILE.toPath(), EXPECTED_CONTENTS.getBytes());
            readerService = new ReaderServiceCsv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void cleanup() throws IOException {
        Files.deleteIfExists(TEST_FILE.toPath());
        Files.deleteIfExists(TMP_DIR);
    }

    @Test
    public void readLines_withValidFile_returnsListOfLines() throws IOException {
        List<String> expectedLines = Arrays.asList(EXPECTED_CONTENTS.split("\n"));
        List<String> actualLines = readerService.readLines(TEST_FILE);
        Assertions.assertEquals(expectedLines, actualLines);
    }

    @Test
    public void readLines_withNonExistentFile_throwsFileNotFoundException() {
        File nonExistentFile = new File(NONEXISTENT_FILE_CSV);
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readLines(nonExistentFile));
    }

    @Test
    public void readLines_nullFile_throwsRuntimeException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readLines(null));
    }
}
