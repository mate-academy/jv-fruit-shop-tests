package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceCsvTest {
    private static final Collection<String> LINES_TO_WRITE
            = Arrays.asList("line1", "line2");
    private static final String READER_TEST_EXCEPTION
            = "Exception while deleting file after reader test";
    private static final String READING_EXCEPTION
            = "Exception while reading file ";
    private static WriterServiceCsv writerService;
    private static final String FILE_PATH_NOT_EXISTING_EMPTY
            = "src/test/resources/new_empty_file.csv";
    private static final String FILE_PATH_EXISTING_EMPTY
            = "src/test/resources/empty_file.csv";
    private static final String FILE_PATH_EXPECTED
            = "src/test/resources/expected.csv";

    @BeforeClass
    public static void setup() {
        writerService = new WriterServiceCsv();
    }

    @After
    public void clear() {
        try {
            new FileWriter(FILE_PATH_EXISTING_EMPTY, false).close();
            new File(FILE_PATH_NOT_EXISTING_EMPTY).delete();
        } catch (IOException e) {
            throw new RuntimeException(READER_TEST_EXCEPTION);
        }

    }

    @Test
    public void writeLines_withExistingFile_ok() {
        writerService.writeLines(
                LINES_TO_WRITE,
                new File(FILE_PATH_EXISTING_EMPTY));
        Assert.assertEquals(
                read(FILE_PATH_EXISTING_EMPTY),
                read(FILE_PATH_EXPECTED));
    }

    @Test
    public void writeLines_withNonExistingFile_ok() {
        writerService.writeLines(
                LINES_TO_WRITE,
                new File(FILE_PATH_NOT_EXISTING_EMPTY));
        Assert.assertEquals(
                read(FILE_PATH_NOT_EXISTING_EMPTY),
                read(FILE_PATH_EXPECTED));
    }

    private Collection<String> read(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(READING_EXCEPTION + filename);
        }
    }
}
