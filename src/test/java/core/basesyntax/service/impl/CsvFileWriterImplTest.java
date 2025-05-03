package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class CsvFileWriterImplTest {

    private static final String pathToFile = "src/test/resources/fileWriterTest.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90";
    private final CsvFileWriter fileWriter = new CsvFileWriterImpl();

    @Test
    public void write_validData_Ok() throws IOException {
        fileWriter.write(REPORT, pathToFile);
        String expectedAfterRead = "[fruit,quantity, banana,152, apple,90]";
        Path path = Paths.get(pathToFile);
        Stream<String> lines = Files.lines(path);
        List<String> actualRead = lines.toList();

        assertEquals(expectedAfterRead, actualRead.toString());
    }

    @Test
    public void write_nullFilename_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(REPORT, null));
    }
}
