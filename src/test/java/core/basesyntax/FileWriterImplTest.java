package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    public static final String PATH = "src/test/resources/fileToWrite.csv";
    public static final String NOT_EXISTENT_FILE = "";
    private static FileWriter writer;
    private static FileReader reader;

    @BeforeAll
    static void beforeAll() {
        writer = new FileWriterImpl();
        reader = new FileReaderImpl();
    }

    @Test
    void write_existentFile_Ok() {
        String content = "Hello, World!";
        writer.write(content, PATH);
        List<String> strings = reader.readLinesFromFile(PATH);
        for (int i = 0; i < strings.size(); i++) {
            assertEquals(content.split("\n")[i], strings.get(i));
        }
    }

    @Test
    void write_notExistentFile_notOk() {
        String content = "Hello";
        assertThrows(RuntimeException.class, () -> writer.write(content, NOT_EXISTENT_FILE));
    }
}
