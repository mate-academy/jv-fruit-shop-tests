package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class WriterServiceTest {
    private static final String VALID_FILE = "src/test/java/core/basesyntax/resources/writer/valid_report_file.csv";
    private static final String VALID_FILE_REFERENCE = "src/test/java/core/basesyntax/resources/writer/valid_report_file_reference.csv";
    private static final String NON_EXISTENT_FOLDER = "src/test/this_folder_doesnt_exist/output.csv";
    private static final String EMPTY_FILE_REFERENCE = "src/test/java/core/basesyntax/resources/writer/empty_file_reference.csv";
    private static final long IS_IDENTICAL = -1L;
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
        assertTrue(isIdenticalFiles(VALID_FILE, VALID_FILE_REFERENCE));
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
        assertTrue(isIdenticalFiles(VALID_FILE, EMPTY_FILE_REFERENCE));
    }


    private boolean isIdenticalFiles(String firstPath, String secondPath) {
        long result = 0;
        try {
            result = Files.mismatch(Path.of(firstPath), Path.of(secondPath));
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong when comparing files by path: "
                                                            + firstPath + " and " + secondPath, e);
        }
        return result == IS_IDENTICAL;
    }
}

