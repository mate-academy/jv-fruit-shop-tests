package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    @TempDir
    private Path tempDir;

    @Test
    void read_returnStringsWithoutHeader() throws IOException {

        Path file = tempDir.resolve("test.csv");
        List<String> strings = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );

        Files.write(file, strings);
        FileReader reader = new FileReaderImpl();

        List<String> result = reader.read(file.toString());

        assertEquals(3, result.size());
        assertEquals("b,banana,20", result.get(0));
        assertEquals("s,banana,100", result.get(2));

    }

    @Test
    void read_nonExistingFile_throwsException() {
        FileReader reader = new FileReaderImpl();

        String fakePath = "not_existing_file.csv";

        assertThrows(RuntimeException.class, () -> reader.read(fakePath));
    }
}
