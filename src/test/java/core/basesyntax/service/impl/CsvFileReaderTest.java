package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderServ;
import java.nio.file.Path;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String VALID_FILE_PATH =
            "src/test/resources/input.csv";
    private static final String NON_EXISTENT_PATH =
            "invalid/path/to/file";
    private static final String EMPTY_FILE_PATH =
            "src/test/resources/empty.csv";
    private FileReaderServ fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CsvFileReader();
    }

    @Test
    void read_inputIncorrectFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(NON_EXISTENT_PATH));
    }

    @Test
    void read_readAllLinesFromInputFile_ok() {
        Collection<String> readLines = fileReader.read(VALID_FILE_PATH);
        Path path = Path.of(VALID_FILE_PATH);
        assertEquals(9, readLines.size(),
                "List size is not as expected. Not all data read from file: "
                        + path.getFileName());
    }

    @Test
    void read_readDataFromEmptyInputFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(EMPTY_FILE_PATH));
    }
}
