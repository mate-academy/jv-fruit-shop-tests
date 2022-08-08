package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/outputFile.csv";
    private static final String WRONG_OUTPUT_FILE_PATH = "src/test/resources/docs/outputFile.csv";
    private static FileWriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @After
    public void tearDown() {
        new File(OUTPUT_FILE_PATH).delete();
    }

    @Test
    public void writeToFile_defaultCase_Ok() {
        Util.createNewFolder();
        writerService.writeToFile(OUTPUT_FILE_PATH, Util.createTextFromLines(Util.REPORT));
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + OUTPUT_FILE_PATH, e);
        }
        String expected = Util.createTextFromLines(Util.REPORT);
        String actual = lines.stream().collect(Collectors.joining(System.lineSeparator()));
        assertEquals("Expected should be equal to "
                + expected + " but was: "
                + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_FileCanNotBeReached_notOk() {
        Util.createNewFolder();
        writerService.writeToFile(WRONG_OUTPUT_FILE_PATH, Util.createTextFromLines(Util.REPORT));
    }
}
