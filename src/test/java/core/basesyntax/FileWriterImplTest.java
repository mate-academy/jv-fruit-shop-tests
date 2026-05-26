package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class FileWriterImplTest {
    private FileWriterImpl writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void write_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("writer-test", ".csv");
        List<String> lines = List.of("fruit,quantity", "apple,10", "banana,20");
        String data = String.join("\n", lines);
        writer.write(data, tempFile.toString());
        List<String> actual = Files.readAllLines(tempFile);
        assertEquals(lines, actual);
    }
}
