package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String PATH = "src/main/resources/database.csv";
    private static final String INCORRECT_PATH = "/incorrect/path";

    @Test
    public void readInfoFromFileOk() {
        FileWriter writer = new FileWriterImpl();
        writer.write("banana,152\napple,90", PATH);

        FileReader reader = new FileReaderImpl();
        List<String> actual = reader.read(PATH);

        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");

        assertEquals(actual, expected);

    }

    @Test
    public void fileNotFoundExeptionOk() {
        FileReader reader = new FileReaderImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read(INCORRECT_PATH));
        assertEquals("Can't read from file", exception.getMessage());
    }
}
