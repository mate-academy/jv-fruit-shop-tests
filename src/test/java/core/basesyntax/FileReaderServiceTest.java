package core.basesyntax;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReaderServiceTest {
    private static final String CORRECT_INPUT_FILE_NAME = "src/main/resources/fruits.csv";
    private static final String WRONG_INPUT_FILE_NAME = "src/main/resources/fruits100.csv";
    private static FileReaderService fileReaderService;

    @BeforeAll
    public static void initializeFileReader() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readingInformationFromNotExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readRowsFromFile(WRONG_INPUT_FILE_NAME));
    }

    @Test
    public void readingInformationFromFile_Ok() {
        fileReaderService.readRowsFromFile(CORRECT_INPUT_FILE_NAME);
    }
}
