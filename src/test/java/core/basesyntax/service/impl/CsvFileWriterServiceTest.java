package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceTest {
    private static CsvFileWriterService csvFileWriterService;

    @BeforeAll
    static void beforeAll() {
        csvFileWriterService = new CsvFileWriterService();
    }

    @Test
    void writeToFile_correctPath_ok() {
        String output = new StringBuilder()
                .append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,90").toString();
        String pathString = "src/test/resources/output.csv";
        Path path = Path.of(pathString);

        csvFileWriterService.writeToFile(pathString, output);
        List<String> actualList;
        try {
            actualList = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String actual = actualList.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(output, actual);
    }

    @Test
    void readFromFile_incorrectFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeToFile("incorrect/file/path", "some output"));
    }
}
