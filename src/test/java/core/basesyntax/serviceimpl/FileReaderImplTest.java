package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String FILE_PATH = "jv-fruit-shop-tests/src/test/resources/input.csv";
    private static final String INVALID_FILE_PATH = "input.csv";
    private static final List<String> EXPECTED_FILE_CONTENT = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static FileReaderImpl fileReader;

    @BeforeAll
    static void beforeAll() throws IOException {
        Files.write(Path.of(FILE_PATH), EXPECTED_FILE_CONTENT);
        fileReader = new FileReaderImpl();
    }

    @AfterAll
    static void afterAll() throws IOException {
        Files.delete(Path.of(FILE_PATH));
    }

    @Test
    void read_validInput_ok() {
        List<String> actual = fileReader.read(FILE_PATH);
        assertEquals(EXPECTED_FILE_CONTENT, actual);
    }

    @Test
    void read_invalidInput_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(INVALID_FILE_PATH);
        });
    }

    @Test
    void read_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(null);
        });
    }
}
