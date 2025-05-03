package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.ReadingException;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFile_readFileSuccessfully_ok() throws IOException {
        List<String> expectedList = List.of("one", "two");
        String fileName = "src/test/resources/InputFile.csv";
        Path path = Path.of(fileName);
        Files.write(path, expectedList);
        List<String> actualList = fileReaderService.readFile(path.toString());
        assertEquals(expectedList, actualList);
    }

    @Test
    void readFile_fileNotFound_notOk() {
        String fileName = "src/test/resources/nonFile.csv";
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> fileReaderService.readFile(fileName));
        String actualException = exception.getMessage();
        String expectedException = "Can't read input file: " + fileName;
        assertEquals(expectedException, actualException);
    }

    @Test
    void readFile_fileIsEmpty_ok() throws IOException {
        Path path = Path.of("src/test/resources/emptyFile.csv");
        List<String> actualLines = Files.readAllLines(path);
        assertTrue(actualLines.isEmpty());
    }

    @Test
    void readFile_fileIsNotCsvExpansion_notOk() {
        String fileName = "src/test/resources/notCsvFile.txt";
        ReadingException readingException
                = assertThrows(ReadingException.class, () -> fileReaderService.readFile(fileName));
        String actualMessage = readingException.getMessage();
        String expectedMessage = "The input file isn't csv format.";
        assertEquals(expectedMessage, actualMessage);
    }
}
