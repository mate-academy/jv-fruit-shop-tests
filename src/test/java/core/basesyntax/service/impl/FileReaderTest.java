package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String FILE_PATH = "src/test/java/resource/fruits.csv";
    private static final String INCORRECT_FILE_PATH = "ssrc/test/java/resource/fruits.csv";
    private static FileReaderImpl fileReader;
    private static List<String> expected;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        expected = Arrays.asList("type,fruit,quantity", "b,banana,120",
                "b,apple,60", "b,watermelon,5",
                "p,banana,20", "p,apple,10",
                "r,banana,10", "p,watermelon,2",
                "p,banana,80", "s,banana,200");
    }

    @Test
    void read_correctFilePath_Ok() {
        List<String> actual = fileReader.read(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void read_incorrectFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INCORRECT_FILE_PATH));
    }
}
