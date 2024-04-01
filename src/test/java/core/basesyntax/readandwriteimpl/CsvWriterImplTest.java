package core.basesyntax.readandwriteimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterImplTest {
    private static final CsvWriterImpl CSV_WRITER = new CsvWriterImpl();
    private static final List<String> LINES = Arrays.asList("apple,100", "banana,50");
    private static final String FILE_PATH = "output.csv";

    private CsvWriterImpl csvWriter;
    private List<String> lines;
    private String filePath;

    @BeforeEach
    void setUp() {
        csvWriter = CSV_WRITER;
        lines = LINES;
        filePath = FILE_PATH;
    }

    @AfterEach
    public void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    void writeLinesToFile_ValidLines_WritesToFile_Ok() throws IOException {
        csvWriter.writeLinesToFile(lines, filePath);
        String expectedContent = String
                .join(System.lineSeparator(), lines) + System.lineSeparator();
        String actualContent = Files.readString(Paths.get(filePath));
        assertEquals(expectedContent, actualContent);
    }
}
