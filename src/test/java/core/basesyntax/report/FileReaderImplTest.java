package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private static final String READ_FILE_TEST = "src/test/java/resources/reportToReadTest.csv";
    private final FileReaderCustom fileReader = new FileReaderImpl();

    @Test
    public void read_validFile_ok() throws IOException {
        Files.write(Path.of(READ_FILE_TEST), List.of("b,banana,20", "b,apple,100", "s,banana,100"));
        List<String> lines = fileReader.read(READ_FILE_TEST);
        assertEquals(List.of("b,banana,20", "b,apple,100", "s,banana,100"), lines);
    }

    @Test
    public void read_invalidFile_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> fileReader.read("invalid.csv"),
                "Invalid file for reading:" + READ_FILE_TEST
                        + "Check resource file format for reading!");
    }

    @Test
    public void read_largeFile_returnsListOfLines() throws IOException {
        Files.write(Path.of(READ_FILE_TEST), IntStream.range(0, 1000)
                .mapToObj(i -> "line" + i).toList());
        List<String> lines = fileReader.read(READ_FILE_TEST);
        assertEquals(1000, lines.size());
    }
}
