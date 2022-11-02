package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {
    private static final String FILE_PATH_EXIST = "src/main/resources/report.csv";
    private static final String FILE_PATH_NOT_EXIST = "src/main/files/report.csv";
    private static final String REPORT_SEPARATOR = System.lineSeparator();
    private static final String TEST_REPORT = "fruit,quantity" + REPORT_SEPARATOR
            + "banana,152" + REPORT_SEPARATOR + "apple,90";

    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    void writeToFile_ExistPath_Ok() {
        fileWriterService.writeToFile(FILE_PATH_EXIST, TEST_REPORT);
        String actual = readFromFile();
        assertEquals(TEST_REPORT, actual);
    }

    @Test
    void readFromFile_NotExistFile_notOk() {
        String report = "wrong_report";
        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeToFile(FILE_PATH_NOT_EXIST, report);
        });
    }

    private String readFromFile() {
        try {
            return Files.readString(Path.of(FileWriterServiceTest.FILE_PATH_EXIST));
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
    }
}
