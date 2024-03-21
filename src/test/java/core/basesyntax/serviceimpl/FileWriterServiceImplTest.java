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
        assertThrows(FileNotExistException.class, () -> {
            fileWriterService.writeToFile(INCORRECT_PATH, HEADER);
        }, "Expected " + FileNotExistException.class.getName() + " but wasn't!");
    }

    @Test
    void writeFile_writeToFile_Ok() {
        fileWriterService.writeToFile(PATH_TO_FILE_OK, EXPECTED_REPORT);
        try {
            List<String> readAllLines = Files.readAllLines(Path.of(PATH_TO_FILE_OK));
            assertEquals(listOk, readAllLines);
        } catch (IOException e) {
            throw new FileNotExistException("Failed to read the content from the file: "
                    + PATH_TO_FILE_OK);
        }
    }
}
