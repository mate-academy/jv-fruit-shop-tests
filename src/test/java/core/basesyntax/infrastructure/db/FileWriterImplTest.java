package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String PATH = "src/main/resources/database.csv";
    private static final String INCORRECT_PATH = "/incorrect/path";

    @Test
    public void writeIntoFileOk() {
        String expectedMessage = "expected message";
        List<String> expected = new ArrayList<>();
        expected.add("expected message");
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write("expected message", PATH);
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.read(PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void incorrectPathNotToWriteOk() {
        FileWriter fileWriter = new FileWriterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write("", INCORRECT_PATH));

        assertEquals("Can't open the file: " + INCORRECT_PATH, exception.getMessage());
    }
}
