package core.basesyntax;

import core.basesyntax.service.impl.CsvFileWriterImpl;
import core.basesyntax.service.writer.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterTest {
    private static final String filepath = "report.csv";
    private static CsvFileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriterImpl();
    }

    @Test
    void testWriter_Ok() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13";
        fileWriter.writeDataToFile(filepath, expected);
        String actual;
        try {
            actual = Files.readString(Path.of(filepath));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file " + filepath, e);
        }
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeDataToNoNameFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeDataToFile("", "data"));
    }
}
