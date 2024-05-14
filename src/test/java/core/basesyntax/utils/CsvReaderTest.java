package core.basesyntax.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvReaderTest {
    @Test
    void readLines_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".scs");
        Files.write(tempFile, List.of("b,apple,100", "s,apple,20", "p,apple,10", "r,apple,5"));
        List<String> lines = CsvReader.readLines(tempFile.toString());
        assertEquals(4, lines.size());
        assertEquals("b,apple,100", lines.get(0));
        assertEquals("s,apple,20", lines.get(1));
        assertEquals("p,apple,10", lines.get(2));
        assertEquals("r,apple,5", lines.get(3));
        Files.delete(tempFile);
    }

    @Test
    void readLines_fileNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> CsvReader.readLines("non_existing_file.csv"));
    }
}
