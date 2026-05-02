package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.basesyntax.service.FileWriter;
import core.basesyntax.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter writer;

    @BeforeAll
    static void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void writeFile_validData_ok() throws IOException {
        Path tempFile = Files.createTempFile("output", ".csv");

        List<String> data = List.of(
                "fruit,quantity",
                "banana,20"
        );
        writer.writeFile(data, tempFile.toString());

        List<String> result = Files.readAllLines(tempFile);

        assertEquals(data, result);
    }

    @Test
    void writeFile_emptyData_ok() throws IOException {
        Path tempFile = Files.createTempFile("output_empty", ".csv");
        writer.writeFile(List.of(), tempFile.toString());
        List<String> result = Files.readAllLines(tempFile);

        assertTrue(result.isEmpty());
    }

    @Test
    void writeFile_invalidPath_notOK() {
        List<String> data = List.of("test");

        assertThrows(RuntimeException.class,
                () -> writer.writeFile(data, null));
    }
}
