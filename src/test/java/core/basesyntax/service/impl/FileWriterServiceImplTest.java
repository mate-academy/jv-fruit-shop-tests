package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String emptyFileName = "";
    private static final String report = "Test string";
    private static final String readFilePath = "src/main/resources/file.csv";
    private static final String writeToFilePath = "src/main/resources/file.csv";
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void writeEmptyFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToCsvFile(emptyFileName, report));
    }

    @Test
    void writeNullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToCsvFile(null, report));
    }

    @Test
    void writeValidData_ok() {
        String excepted = "Test string";
        fileWriterService.writeToCsvFile(writeToFilePath, excepted);
        String actual = readData(readFilePath);
        assertEquals(excepted, actual);
    }

    private String readData(String path) {
        try {
            List<String> strList = Files.readAllLines(Path.of(path));
            return strList.stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
