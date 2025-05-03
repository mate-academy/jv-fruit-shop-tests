package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String REPORT_NAME = "src/test/resources/report.csv";
    private static FileWriter csvfileWriter;

    @BeforeAll
    static void beforeAll() {
        csvfileWriter = new CsvFileWriter();
    }

    @Test
    public void writeTextToFile_Ok() throws IOException {
        String text = "Hello, world!";
        csvfileWriter.writeTextToFile(REPORT_NAME, text);
        String content = new String(Files.readAllBytes(Paths.get(REPORT_NAME)));
        assertEquals(text, content);
    }

    @Test
    void writeTextToFile_invalidName_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvfileWriter.writeTextToFile(null, "text"),
                "Writing to null file should throw exception");
        assertThrows(RuntimeException.class,
                () -> csvfileWriter.writeTextToFile(REPORT_NAME, null),
                "Writing null text file should throw exception");
    }
}
