package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImpTest {
    private static final String REPORT_TO_READ_FILE = "src/test/resources/reportToReadCorrect.csv";
    private static final String REPORT_TO_READ_WRONG_FILE = "src/test/resources/wrongFile.csv";
    private static FileReaderImpl fileReader;

    @BeforeAll
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFileSize_Ok() {
        int size = fileReader.read(REPORT_TO_READ_FILE).size();
        assertEquals(9, size);
    }

    @Test
    void correctReadingFromFile_Ok() {
        List<String> reportList = fileReader.read(REPORT_TO_READ_FILE);
        assertEquals("type,fruit,quantity", reportList.get(0));
        assertEquals("b,banana,20", reportList.get(1));
        assertEquals("b,apple,1000", reportList.get(2));
        assertEquals("s,banana,100", reportList.get(3));
    }

    @Test
    void wrongFileToRead_NotOk() {
        Path path = Paths.get(REPORT_TO_READ_WRONG_FILE);
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(REPORT_TO_READ_WRONG_FILE));
        String exceptedMessage = "Error writing to file at path: " + path;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(exceptedMessage));
    }
}
