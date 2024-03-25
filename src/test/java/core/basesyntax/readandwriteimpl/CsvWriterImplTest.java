package core.basesyntax.readandwriteimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterImplTest {
    private CsvWriterImpl csvWriter;
    private List<String> lines;
    private String filePath;

    @BeforeEach
    void setUp() {
        csvWriter = new CsvWriterImpl();
        lines = Arrays.asList("apple,100", "banana,50");
        filePath = "output.csv";
    }

    @AfterEach
    public void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    void writeLinesToFile() throws IOException {
        csvWriter.writeLinesToFile(lines, filePath);
        String expectedContent = String
                .join(System.lineSeparator(), lines) + System.lineSeparator();
        String actualContent = Files.readString(Paths.get(filePath));
        assertEquals(expectedContent, actualContent);
    }
}
