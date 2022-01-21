package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_INPUT_FILE_NAME = "src/main/resources/fruits.csv";
    private static final String WRONG_INPUT_FILE_NAME = "src/main/resources/fruits100.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void initialize() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readingInformationFromNotExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readRowsFromFile(WRONG_INPUT_FILE_NAME));
    }

    @Test
    public void readingInformationFromFile_Ok() {
        fileReaderService.readRowsFromFile(CORRECT_INPUT_FILE_NAME);
    }
}
