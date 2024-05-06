package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String CORRECT_STORAGE_FILE_PATH = "src/test/resources/storage.csv";
    private static final String WRONG_STORAGE_FILE_PATH = "src/test/resources/notExistingFile.csv";
    private static final String EMPTY_STORAGE_FILE_PATH = "src/test/resources/emptyStorage.csv";
    private static final String CORRECT_REPORT_FILE_PATH = "src/test/resources/report.csv";
    private static final String WRONG_REPORT_FILE_PATH = "src/test/resources/notExistingReport.csv";
    private static final String NOT_EMPTY_REPORT_FILE_PATH
            = "src/test/resources/notEmptyReport.csv";
    private static final List<String> expected =
                                            List.of("type,fruit,quantity",
                                                    "b,banana,20",
                                                    "b,apple,100",
                                                    "s,banana,100",
                                                    "p,banana,13",
                                                    "r,apple,10",
                                                    "p,apple,20",
                                                    "p,banana,5",
                                                    "b,berry,5",
                                                    "s,berry,10",
                                                    "p,berry,1",
                                                    "r,berry,1");
    private static final String report = "report text";
    private final FileService fileService = new FileServiceImpl();

    @Test
    void readFile_correctFilePath_Ok() {
        List<String> actual = fileService.readFile(CORRECT_STORAGE_FILE_PATH);
        assertEquals(expected, actual,
                "If path is correct, data from the file must be formed into List");
    }

    @Test
    void readFile_wrongFilePath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileService.readFile(WRONG_STORAGE_FILE_PATH),
                "If path is not correct, exception should be thrown");
    }

    @Test
    void readFile_nullFilePath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileService.readFile(null),
                "If path is null, exception should be thrown");
    }

    @Test
    void readFile_emptyFile_notOk() {
        List<String> actual = fileService.readFile(EMPTY_STORAGE_FILE_PATH);
        assertTrue(actual.isEmpty(),
                "If file is empty, List should be empty");
        makeFileEmpty(EMPTY_STORAGE_FILE_PATH);
    }

    @Test
    void writeToFile_correctFilePath_Ok() {
        fileService.writeToFile(report, CORRECT_REPORT_FILE_PATH);
        try {
            BufferedReader reader =
                         new BufferedReader(new FileReader(CORRECT_REPORT_FILE_PATH));
            String actual = reader.readLine();
            assertEquals(report, actual, "Text intended to be written "
                    + "does not match the text written");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file to compare values: " + e);
        }
        makeFileEmpty(CORRECT_REPORT_FILE_PATH);
    }

    @Test
    void writeToFile_wrongFilePath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileService.writeToFile(report, WRONG_REPORT_FILE_PATH),
                "Exception should be thrown if file does not exist");
    }

    @Test
    void writeToFile_nullFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(report, WRONG_REPORT_FILE_PATH),
                "Exception should be thrown if file path is null");
    }

    @Test
    void writeToFile_reportToNotEmptyFile_notOk() {
        makeFileNotEmpty(NOT_EMPTY_REPORT_FILE_PATH);
        fileService.writeToFile(report, NOT_EMPTY_REPORT_FILE_PATH);
        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(NOT_EMPTY_REPORT_FILE_PATH));
            String actual = reader.readLine();
            assertEquals(report, actual, "Text intended to be written "
                    + "does not match the text written");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file to compare values: " + e);
        }
        makeFileNotEmpty(NOT_EMPTY_REPORT_FILE_PATH);
    }

    @Test
    void writeToFile_emptyReportToNotEmptyFile_notOk() {
        makeFileNotEmpty(NOT_EMPTY_REPORT_FILE_PATH);
        fileService.writeToFile("", NOT_EMPTY_REPORT_FILE_PATH);
        try {
            List<String> actual = Files.readAllLines(Path.of(NOT_EMPTY_REPORT_FILE_PATH));
            assertTrue(actual.isEmpty(),"If report is empty, file also must be empty");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file to compare values: " + e);
        }
        makeFileNotEmpty(NOT_EMPTY_REPORT_FILE_PATH);
    }

    @Test
    void writeToFile_emptyReportToEmptyFile_notOk() {
        fileService.writeToFile("", CORRECT_REPORT_FILE_PATH);
        try {
            List<String> actual = Files.readAllLines(Path.of(CORRECT_REPORT_FILE_PATH));
            assertTrue(actual.isEmpty(),"If report is empty, file also must be empty");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file to compare values: " + e);
        }
        makeFileNotEmpty(CORRECT_REPORT_FILE_PATH);
    }

    @Test
    void writeToFile_nullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileService.writeToFile(null, CORRECT_REPORT_FILE_PATH),
                "If report is null, exception should be thrown");
    }

    private void makeFileEmpty(String filePath) {
        try {
            Files.writeString(Path.of(filePath), "");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + e);
        }
    }

    private static void makeFileNotEmpty(String filePath) {
        try {
            Files.writeString(Path.of(filePath), "type,fruit,quantity"
                    + System.lineSeparator() + "b,banana,20"
                    + System.lineSeparator() + "b,apple,100"
                    + System.lineSeparator() + "s,banana,100");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + e);
        }
    }

}
