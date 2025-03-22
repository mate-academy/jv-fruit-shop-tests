package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    @Test
    void writeIntoFileOk() {
        String expectedMessage = "expected message";
        List<String> expected = new ArrayList<>();
        expected.add("expected message");
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write("expected message", "src/main/resources/database.csv");
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.read("src/main/resources/database.csv");
        assertEquals(expected, actual);
    }

    @Test
    void incorrectPathNotToWriteOk() {
        FileWriter fileWriter = new FileWriterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write("", "/invalid/path/notPath"));

        assertEquals("Can't open the file: /invalid/path/notPath", exception.getMessage());
    }
}
