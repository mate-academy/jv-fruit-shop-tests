package file.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final int ZERO_INDEX = 0;
    private static final String EMPTY_LINE = "";
    private static final String START_PATH = "./src/main/resources/";
    private static final String VALID_FILE_NAME = "finalReportTest.csv";
    private static final String NO_CSV_FILE_FORMAT = "invalidFileFormat";
    private static final String INVALID_FILE_PATH = "./sr/invalidFileFormat.csv";
    private static final String VALID_WRITE_CONTENT = "fruit,quantity"
            + System.lineSeparator() + "b,banana,20";
    private static FileWriter fileWriter;

    @BeforeAll
    static void createRegistrationService() {
        fileWriter = new FileWriterImpl();
        fileWriter.writeToCsv(VALID_WRITE_CONTENT, VALID_FILE_NAME);
    }

    @Test
    void write_validList_ok() throws FileNotFoundException {
        StringBuilder actual = new StringBuilder();
        Scanner scanner = new Scanner(new File(START_PATH + VALID_FILE_NAME));
        while (scanner.hasNextLine()) {
            actual.append(scanner.nextLine()).append(System.lineSeparator());
        }
        assertEquals(VALID_WRITE_CONTENT,
                actual.substring(ZERO_INDEX, actual.lastIndexOf(System.lineSeparator())));
    }

    @Test
    void write_nullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToCsv(VALID_WRITE_CONTENT, null));
    }

    @Test
    void write_nullInputValue_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToCsv(null, VALID_FILE_NAME));
    }

    @Test
    void write_nullParameter_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToCsv(null, null));
    }

    @Test
    void write_invalidFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToCsv(VALID_WRITE_CONTENT, NO_CSV_FILE_FORMAT));
    }

    @Test
    void write_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToCsv(VALID_WRITE_CONTENT, INVALID_FILE_PATH));
    }

    @Test
    void write_emptyReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToCsv(EMPTY_LINE, VALID_FILE_NAME));
    }
}
