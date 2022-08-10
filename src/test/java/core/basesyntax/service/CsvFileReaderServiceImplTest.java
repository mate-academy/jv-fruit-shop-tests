package core.basesyntax.service;

import static core.basesyntax.service.Util.INPUT_FILE_LINES;
import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static FileReaderService readerService;
    private static final String PATH_TO_FILE = "src/test/resources/inputFile.csv";

    @BeforeClass
    public static void beforeClass() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @After
    public void tearDown() {
        new File(PATH_TO_FILE).delete();
    }

    @Test
    public void readFromFile_validData_Ok() {
        Util.createNewFolder();
        writeTestDataToFile();
        List<String> expected = INPUT_FILE_LINES;
        List<String> actual = readerService.readFromFile(PATH_TO_FILE);
        assertEquals("readFromLine should return List of file's lines: "
                + expected + " but was: "
                + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileDoesNotExist_notOk() {
        readerService.readFromFile(PATH_TO_FILE);
    }

    public static void writeTestDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_FILE))) {
            writer.write(Util.createTextFromLines(INPUT_FILE_LINES));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + PATH_TO_FILE, e);
        }
    }
}
