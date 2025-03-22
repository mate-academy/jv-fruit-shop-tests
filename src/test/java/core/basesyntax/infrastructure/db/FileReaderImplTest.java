package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    @Test
    void readInfoFromFileOk() {
        FileWriter writer = new FileWriterImpl();
        writer.write("banana,152\napple,90", "src/main/resources/database.csv");

        FileReader reader = new FileReaderImpl();
        List<String> actual = reader.read("src/main/resources/database.csv");

        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");

        assertEquals(actual, expected);

    }

    @Test
    void fileNotFoundExeptionOk() {
        FileReader reader = new FileReaderImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read("/incorrect/path"));
        assertEquals("Can't read from file", exception.getMessage());
    }
}
