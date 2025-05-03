package service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.impl.FileReaderImpl;

class ReaderTest {
    private static final String FILE_NAME_INPUT_DATA_CORRECT
            = "src/test/resources/inputDateFile.csv";
    private static final String FILE_NAME_INPUT_DATA_NULL
            = "src/test/resources/inputDateFileNull.csv";
    private static final Reader reader = new FileReaderImpl();

    @Test
    void readFile_Ok() {
        List<String> expected = List.of("b,banana,100", "s,banana,20",
                "p,banana,10", "r,banana,5");
        List<String> actual = reader.readFile(FILE_NAME_INPUT_DATA_CORRECT);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void readNullFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.readFile(FILE_NAME_INPUT_DATA_NULL);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void readNoSuchFile_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.readFile("no file"));
    }
}
