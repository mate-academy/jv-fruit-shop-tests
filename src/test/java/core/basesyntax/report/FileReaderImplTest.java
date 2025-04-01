package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private static String READ_FILE = "src/main/resources/reportToRead.csv";
    private final FileReaderCustom fileReader = new FileReaderImpl();

    @BeforeEach
    void name() {
        READ_FILE = "src/main/resources/reportToRead.csv";
        fileReader.read(READ_FILE);
    }

    @Test
    public void read_validFile_returnsListOfLines() throws IOException {
        Files.write(Path.of(READ_FILE), List.of("b,banana,20", "b,apple,100", "s,banana,100"));
        List<String> lines = fileReader.read(READ_FILE);
        assertEquals(List.of("b,banana,20", "b,apple,100", "s,banana,100"), lines);
    }

    @Test
    public void read_invalidFile_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> fileReader.read("invalid.csv"),
                "Invalid file for reading:" + READ_FILE
                        + "Check resource file format for reading!");
    }

    @Test
    void read_emptyFile() {
        if (READ_FILE.isEmpty()) {
            Assertions.fail("You should have data in input file for reading: " + READ_FILE);
        }
    }

    @Test
    void read_validFile_correctFirsLine() throws FileNotFoundException {
        String firstLine = "b,banana,20";
        READ_FILE = "src/main/resources/reportToRead.csv";
        assertEquals(firstLine, fileReader.read(READ_FILE).get(0));
        if (!firstLine.equals(fileReader.read(READ_FILE).get(0))) {
            Assert.fail("Check the correct HEAD and the structure of the read file: " + READ_FILE
                    + System.lineSeparator() + "Should be: " + firstLine);
        }
    }

    @Test
    public void read_largeFile_returnsListOfLines() throws IOException {
        // Створення великого тестового файлу
        Files.write(Path.of(READ_FILE), IntStream.range(0, 1000)
                .mapToObj(i -> "line" + i).toList());
        List<String> lines = fileReader.read(READ_FILE);
        assertEquals(1000, lines.size());
    }
}
