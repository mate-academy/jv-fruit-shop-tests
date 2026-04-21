package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.io.CsvFileReaderImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {

    @Test
    void read_shouldThrowException_whenFilePathIsNull() {
        CsvFileReaderImpl reader = new CsvFileReaderImpl();

        assertThrows(RuntimeException.class,
                () -> reader.read(null));
    }

    @Test
    void read_shouldThrowException_whenFilePathIsBlank() {
        CsvFileReaderImpl reader = new CsvFileReaderImpl();

        assertThrows(RuntimeException.class,
                () -> reader.read("   "));
    }

    @Test
    void read_shouldReturnEmptyList_whenOnlyHeader() throws Exception {
        Path file = Files.createTempFile("test", ".csv");
        Files.write(file, List.of("header"));

        CsvFileReaderImpl reader = new CsvFileReaderImpl();

        List<String> result = reader.read(file.toString());

        assertEquals(0, result.size());
    }

    @Test
    void read_shouldSkipBlankLinesAndHeader() throws Exception {
        Path file = Files.createTempFile("test", ".csv");

        Files.write(file, List.of(
                "header",
                "b,apple,10",
                "",
                "   ",
                "s,banana,5"
        ));

        CsvFileReaderImpl reader = new CsvFileReaderImpl();

        List<String> result = reader.read(file.toString());

        assertEquals(2, result.size());
    }
}
