package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.ReadFromFileException;
import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final FileReader fileReader = new CsvFileReader();

    @Test
    void readData_FilePathIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> fileReader.readData(null));
    }

    @Test
    void readData_invalidFilePath_notOk() {
        String pathToFile = "src/test/resources/non_existent_file.csv";
        ReadFromFileException expected = assertThrows(ReadFromFileException.class,
                () -> fileReader.readData(pathToFile));
        assertEquals(String.format("Can`t read data from the file %s", pathToFile),
                expected.getMessage());
    }

    @Test
    void readData_fileIsEmpty_Ok() {
        String pathToFile = "src/test/resources/empty_file.csv";
        List<String> actual = fileReader.readData(pathToFile);
        assertTrue(actual.isEmpty());
    }

    @Test
    void readData_returnsListOfLinesFromInputFile_Ok() {
        String pathToFile = "src/test/resources/valid_input.csv";
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
        List<String> actual = fileReader.readData(pathToFile);
        assertEquals(expected, actual);
    }
}
