package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {
    private static final String SEPARATOR = System.lineSeparator();
    private static final String VALID_FILE_NAME = "src/main/resources/input.csv";
    private static final String FILE_NAME_WITH_WRONG_STRUCTURE =
            "src/main/resources/non-validStructure.csv";
    private FileReaderService fileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void getValidReport() {
        List<String> actual = fileReaderService.readFile(new File(VALID_FILE_NAME));
        List<String> expected = new ArrayList<>();
        try {
            expected = Files.readAllLines(new File(VALID_FILE_NAME).toPath())
                    .stream()
                    .skip(1)
                    .map(String::trim)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            fail();
        }
        assertEquals(expected, actual);
    }

    @Test
    void nonValidStructure_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFile(new File(FILE_NAME_WITH_WRONG_STRUCTURE)));
    }

    @Test
    void nonExistingFile_notOK() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFile(new File("wrongFilePath")));
    }
}
