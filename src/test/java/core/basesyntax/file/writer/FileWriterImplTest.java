package core.basesyntax.file.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("output", ".csv");

        String data = "fruit,quantity\nbanana,20";
        fileWriter.write(data, tempFile.toString());

        List<String> result = Files.readAllLines(tempFile);
        assertEquals(2, result.size());
        assertEquals("fruit,quantity", result.get(0));
        assertEquals("banana,20", result.get(1));

        Files.delete(tempFile);
    }

    @Test
    void write_invalidFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "/invalid/path/output.csv"));
    }
}
