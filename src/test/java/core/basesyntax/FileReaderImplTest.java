package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.reader.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFileIncorrectPath_throwsException() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("/invalid-path/does-not-exist/report.txt"));
    }

    @Test
    public void readValidFileSkipsHeaderAndReadsLines_Ok() throws IOException {
        Path tempFile = Files.createTempFile("testData", ".csv");

        List<String> lines = List.of(
                "fruit,quantity",
                "Apple,10",
                "Banana,5"
        );
        Files.write(tempFile, lines);

        List<String> actual = fileReader.read(tempFile.toString());

        assertEquals(List.of("Apple,10", "Banana,5"), actual);
    }

    @Test
    public void readFileOnlyWithHeader_returnsEmptyList() throws IOException {
        Path tempFile = Files.createTempFile("testData", ".csv");

        Files.write(tempFile, List.of("fruit,quantity"));

        List<String> actual = fileReader.read(tempFile.toString());

        assertTrue(actual.isEmpty());
    }
}
