package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService csvFileWriterService;
    private static final String VALID_FILE_PATH = "src//test//resources//Report.csv";
    private static final String INVALID_FILE_PATH = "nonexistent-directory/nonexistent-file.txt";

    @BeforeAll
    static void setUp() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    void writeToFile_writeCorrectInformation_ok() {
        List<String> content = List.of("fruit,quantity", "b,apple,10", "b,banana,15");
        String expected = String.join(System.lineSeparator(), content);
        String fileName = VALID_FILE_PATH;
        csvFileWriterService.writeFile(content, fileName);
        String actual = read(fileName).trim();
        assertEquals(expected, actual);
    }

    @Test
    void writeFile_fileNotFound_notOk() {
        List<String> lines = List.of("Line 1", "Line 2");
        String fileName = INVALID_FILE_PATH;
        assertThrows(RuntimeException.class, () -> csvFileWriterService.writeFile(lines, fileName));
    }

    private String read(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName, e);
        }
    }
}
