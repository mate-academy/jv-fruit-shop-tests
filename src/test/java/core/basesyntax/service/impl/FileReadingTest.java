package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadingTest {
    private static final String INPUT_FILE_CSV = "src/test/java/resources/inputFile.csv";
    private static final String NON_EXISTENT_FILE = "src/test/java/resources/inputFakeFile.csv";
    private static FileService fileService;

    @BeforeClass
    public static void initialize() {
        fileService = new FileReading();
    }

    @Test
    public void readFile_readingInformationFromFile_Ok() {
        fileService.readFile(INPUT_FILE_CSV);
    }

    @Test
    public void readFile_readingInformationFromNonExistentFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.readFile(NON_EXISTENT_FILE));

    }
}
