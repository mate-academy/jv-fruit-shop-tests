package core.basesyntax;

import core.basesyntax.service.impl.CsvFileReaderImpl;
import core.basesyntax.service.reader.CsvFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderTest {
    private static final String filepath = "report.csv";
    private static CsvFileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvFileReaderImpl();
    }

    @Test
    void testReader_Ok() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13";
        try {
            Files.write(Path.of(filepath), expected.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Cannot write data to file " + filepath, e);
        }
        List<String> strings = fileReader.readDataFromFile(filepath);
        String actual = strings.stream().collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testReaderWithNonExistingFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.readDataFromFile("noFile.csv"));
    }
}
