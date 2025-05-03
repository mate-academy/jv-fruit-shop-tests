package core.basesyntax.service.read;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void fileReader_readFile_Ok() {
        String[] actual = fileReader.readFile("src/test/resources/fruits.csv");
        String[] expected = {
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,43",
                "s,banana,19",
                "r,banana,12"
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    void fileReader_readFile_EmptyFile() {
        String[] actual = fileReader.readFile("src/test/resources/empty.csv");
        String[] expected = {};
        assertArrayEquals(expected, actual);
        assertTrue(actual.length == 0);
    }

    @Test
    void fileReader_readFile_fileNotFound() {
        String nonExistentFilePath = ("src/test/resources/noExist_test.csv");
        assertThrows(RuntimeException.class, () -> fileReader.readFile(nonExistentFilePath));
    }
}
