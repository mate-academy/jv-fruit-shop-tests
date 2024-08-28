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

public class FileWriterImplTest {
    private static final String FILE_PATH =
            "src/test/resources/output.csv";
    private static final String WRITE_INPUT = """
            fruit,quantity
            banana,152
            apple,90
            """;
    private static final List<String> EXPECTED_WRITE_RESULT = List.of("fruit,quantity",
            "banana,152",
            "apple,90");
    private static FileWriterImpl fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @AfterAll
    static void afterAll() throws IOException {
        Files.delete(Path.of(FILE_PATH));
    }

    @Test
    void writeFile_validInput_ok() throws IOException {
        fileWriter.writeFile(FILE_PATH, WRITE_INPUT);
        List<String> actual = Files.readAllLines(Path.of(FILE_PATH));
        assertEquals(EXPECTED_WRITE_RESULT, actual);
    }

    @Test
    void writeFile_nullInputs_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeFile(null, null);
        });
    }
}
