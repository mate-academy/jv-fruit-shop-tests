package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderServ;
import java.nio.file.Path;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String INPUT_FILE_PATH =
            "src/test/resources/inputFile.csv";
    private static final String INCORRECT_FILE_PATH =
            "src/test/resources/inputFile.txt";
    private static final String EMPTY_FILE_PATH =
            "src/test/resources/emptyInputFile.csv";
    private FileReaderServ fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CsvFileReader();
    }

    @Test
    void read_inputIncorrectFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(INCORRECT_FILE_PATH));
    }

    @Test
    void read_readAllLinesFromInputFile_Ok() {
        Collection<String> readLines = fileReader.read(INPUT_FILE_PATH);
        Path path = Path.of(INPUT_FILE_PATH);
        assertEquals(9, readLines.size(),
                "List size is not as expected. Not all data read from file: "
                        + path.getFileName());
    }

    @Test
    void read_readDataFromEmptyInputFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(EMPTY_FILE_PATH));
    }
}
