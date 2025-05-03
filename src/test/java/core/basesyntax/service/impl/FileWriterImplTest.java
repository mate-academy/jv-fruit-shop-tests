package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String REPORT_CSV = "src/test/resources/report.csv";
    private static final List<String> EXPECTED = List.of("fruit,quantity", "apple,50", "banana,20");
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(REPORT_CSV));
    }

    @Test
    void writeReport_ok() throws IOException {
        String data = """
                fruit,quantity
                apple,50
                banana,20
                """;
        fileWriter.write(data, REPORT_CSV);
        File file = new File(REPORT_CSV);
        assertTrue(file.exists());
        List<String> actual = Files.readAllLines(Path.of(REPORT_CSV));
        assertEquals(EXPECTED, actual);
    }

    @Test
    void writeReport_emptyData_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fileWriter.write(null, REPORT_CSV));
        assertEquals("Data can not be empty!", exception.getMessage());
    }
}
