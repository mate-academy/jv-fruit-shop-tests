package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String VALID_FILE = "src/test/java/core/basesyntax/resources/"
                                                                  + "writer/valid_report_file.csv";
    private static final String VALID_FILE_REFERENCE = "src/test/java/core/basesyntax/resources/"
                                                        + "writer/valid_report_file_reference.csv";
    private static final String NON_EXISTENT_FOLDER = "src/test/"
                                                           + "this_folder_doesnt_exist/output.csv";
    private static final String EMPTY_FILE_REFERENCE = "src/test/java/core/basesyntax/resources/"
                                                               + "writer/empty_file_reference.csv";
    private static String testReport;
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
        testReport = "fruit,quantity" + System.lineSeparator()
                   + "banana,152" + System.lineSeparator()
                   + "apple,90";
    }

    @Test
    public void writeToFile_ValidFilePathAndReport_Ok() {
        writerService.writeToFile(VALID_FILE, testReport);
        List<String> expected = readFromFile(VALID_FILE_REFERENCE);
        List<String> actual = readFromFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_EmptyFilePathString_NotOk() {
        writerService.writeToFile("", testReport);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_NullFilePath_NotOk() {
        writerService.writeToFile(null, testReport);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_FolderDoesntExistFilePath_NotOk() {
        writerService.writeToFile(NON_EXISTENT_FOLDER, testReport);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_NullReport_NotOk() {
        writerService.writeToFile(VALID_FILE, null);
    }

    @Test
    public void writeToFile_EmptyReport_Ok() {
        writerService.writeToFile(VALID_FILE, "");
        List<String> expected = readFromFile(EMPTY_FILE_REFERENCE);
        List<String> actual = readFromFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file by path: " + filePath, e);
        }
    }
}
