package servise.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String FILE_PATH = "src/test/resources/report.csv";
    private final Writer writer;

    private WriterImplTest() {
        this.writer = new WriterImpl();
    }

    @Test
    void writeToFile_ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "orange,32"
                + System.lineSeparator()
                + "apple,90";
        writer.writeToFile(FILE_PATH, expected);
        String actual = readFromFile(FILE_PATH);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeToFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> writer.writeToFile("", "report"));
    }

    private String readFromFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file" + FILE_PATH, e);
        }
    }
}
