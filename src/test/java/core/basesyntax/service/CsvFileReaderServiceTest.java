package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {
    private static final String SEPARATOR = System.lineSeparator();
    private static final String VALID_FILE_NAME = "src/test/resources/input.csv";
    private static final String FILE_NAME_WITH_WRONG_STRUCTURE =
            "src/test/resources/non-validStructure.csv";
    private FileReaderService fileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void readFile_validFile_Ok() throws IOException {
        List<String> actual = fileReaderService.readFile(new File(VALID_FILE_NAME));
        List<String> expected =
                Files.readAllLines(new File(VALID_FILE_NAME).toPath())
                    .stream()
                    .skip(1)
                    .map(String::trim)
                    .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    void readFile_nonValidFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFile(new File(FILE_NAME_WITH_WRONG_STRUCTURE)));
    }

    @Test
    void readFile_nonExistingFile_notOK() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFile(new File("wrongFilePath")));
    }
}
