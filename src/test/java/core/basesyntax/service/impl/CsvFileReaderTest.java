package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.exception.ReadFromFileException;
import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/non_existent_file.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_file.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/valid_input.csv";
    private final FileReader fileReader = new CsvFileReader();

    @Test
    void readData_FilePathIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> fileReader.readData(null));

        assertEquals("Input file path is null or empty", expected.getMessage());
    }

    @Test
    void readData_FilePathIsEmpty_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> fileReader.readData(""));

        assertEquals("Input file path is null or empty", expected.getMessage());
    }

    @Test
    void readData_invalidFilePath_notOk() {
        ReadFromFileException expected = assertThrows(ReadFromFileException.class,
                () -> fileReader.readData(NON_EXISTENT_FILE_PATH));

        assertEquals(String.format("Can`t read data from the file %s", NON_EXISTENT_FILE_PATH),
                expected.getMessage());
    }

    @Test
    void readData_fileIsEmpty_Ok() {
        List<String> actual = fileReader.readData(EMPTY_FILE_PATH);

        assertTrue(actual.isEmpty());
    }

    @Test
    void readData_returnsListOfLinesFromInputFile_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<String> actual = fileReader.readData(VALID_FILE_PATH);

        assertEquals(expected, actual);
    }
}
