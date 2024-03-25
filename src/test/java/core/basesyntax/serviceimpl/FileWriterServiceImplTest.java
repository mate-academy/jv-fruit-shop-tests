package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileNotExistException;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private static final String INCORRECT_PATH = "main/incorrectPath.csv";
    private static final String PATH_TO_FILE_OK = "src/test/resources/Report.csv";
    private static final String HEADER = "fruit,quantity";
    private static final String SECOND_LINE = "banana,50";
    private static final String EXPECTED_REPORT = HEADER
            + System.lineSeparator() + SECOND_LINE;
    private static List<String> listOk;
    private static FileWriterServiceImpl fileWriterService;

    @BeforeAll
    public static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
        listOk = List.of(HEADER, SECOND_LINE);
    }

    @Test
    void writeToFile_incorrectPath_notOk() {
        FileNotExistException exception = assertThrows(FileNotExistException.class, () -> {
            fileWriterService.writeToFile(INCORRECT_PATH, HEADER);
        });
        assertEquals("Can't write to file: " + INCORRECT_PATH, exception.getMessage());
    }

    @Test
    void writeFile_writeToFile_Ok() throws IOException {
        fileWriterService.writeToFile(PATH_TO_FILE_OK, EXPECTED_REPORT);
        List<String> readAllLines = Files.readAllLines(Path.of(PATH_TO_FILE_OK));
        assertEquals(listOk, readAllLines);
    }

    @Test
    void writeToFile_emptyStringInput_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.writeToFile(PATH_TO_FILE_OK, "");
        });
        assertEquals("Input String cannot be empty", exception.getMessage());
    }
}
