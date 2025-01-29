package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileWriterTest {
    private static final String FILE_TO_WRITE = "src\\main\\resources\\finalReport.csv";
    private static CustomFileWriter customFileWriter;

    @BeforeAll
    static void beforeAll() {
        customFileWriter = new FileWriterImpl();
        customFileWriter.write(FILE_TO_WRITE, "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90");
    }

    @Test
    void writer_contentCheck_ok() throws IOException {
        String expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        Path filePath = Paths.get(FILE_TO_WRITE);
        assertTrue(Files.exists(filePath));
        String readString = Files.readString(filePath);
        assertEquals(expected, readString);
    }

    @Test
    void writer_invalidContent_notOk() throws IOException {
        String expected = "fruit,quantity\n"
                + "banana,153\n"
                + "apple,90";
        Path filePath = Paths.get(FILE_TO_WRITE);
        assertTrue(Files.exists(filePath));
        String readString = Files.readString(filePath);
        assertNotEquals(expected, readString);
    }
}
