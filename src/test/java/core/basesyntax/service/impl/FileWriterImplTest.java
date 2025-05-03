package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.WriteToFileException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String OUTPUT_PATH = "src/main/resources/output.csv";
    private final FileWriter writer = new FileWriterImpl();
    private final List<String> report = List.of(
            "fruit,quantity",
            "product,10",
            "product2,-22"
    );

    @Test
    void writeTo_Ok() {
        writer.writeTo(OUTPUT_PATH, report);
        List<String> actual = readFile(OUTPUT_PATH);
        assertEquals(report, actual);
    }

    @Test
    void writeTo_nullPath_notOk() {
        WriteToFileException existsException = assertThrows(
                WriteToFileException.class,
                () -> writer.writeTo(null, report)
        );
        assertEquals("Can't write to file " + null,
                existsException.getMessage());
    }

    @Test
    void writeTo_nullRows_Ok() {
        writer.writeTo(OUTPUT_PATH, null);
        List<String> actual = readFile(OUTPUT_PATH);
        assertEquals(Collections.emptyList(), actual);
    }

    private List<String> readFile(String path) {
        try {
            return Files.lines(Path.of(path)).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
