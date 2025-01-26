package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileWriterTest {
    private static final String FILE_TO_WRITE = "test.csv";
    private static CustomFileWriter customFileWriter;

    @BeforeAll
    static void beforeAll() {
        customFileWriter = new FileWriterImpl();
        customFileWriter.write(FILE_TO_WRITE, "something");
    }

    @Test
    void writerNullCheck() {
        assertNotNull(customFileWriter);
    }

    @Test
    void writerContentCheck() throws IOException {
        String expected = "something";
        Path filePath = Paths.get(FILE_TO_WRITE);
        assertTrue(Files.exists(filePath));
        String readString = Files.readString(filePath);
        assertEquals(expected, readString);
    }
}
