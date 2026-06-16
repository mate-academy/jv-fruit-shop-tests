package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderImplTest {
    private final FileReaderImpl fileReaderImpl = new FileReaderImpl();

    @TempDir
    private Path tempDirectory;

    @Test
    void read_nullPath_notOk() {
        String path = null;
        assertThrows(IllegalArgumentException.class,
                () -> fileReaderImpl.read(path));
    }

    @Test
    void read_emptyPath_notOk() {
        String path = "";
        assertThrows(IllegalArgumentException.class,
                () -> fileReaderImpl.read(path));
    }

    @Test
    void read_invalidPath_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileReaderImpl.read("invalid/path/toRead"));
    }

    @Test
    void read_validAllData_ok() throws IOException {
        Path path = tempDirectory.resolve("reportToRead.csv");
        List<String> fileContent = List.of(
                "type,fruit,quantity",
                "b,banana,100"
        );

        Files.write(path, fileContent);
        List<String> expectedData = new ArrayList<>();
        expectedData.add("type,fruit,quantity");
        expectedData.add("b,banana,100");
        assertEquals(expectedData, fileReaderImpl.read(path.toString()));
    }
}
