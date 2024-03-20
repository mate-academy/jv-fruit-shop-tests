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
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(REPORT_CSV));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeReport_ok() {
        String data = """
                fruit,quantity
                apple,50
                banana,20
                """;
        fileWriter.write(data, REPORT_CSV);
        File file = new File(REPORT_CSV);
        assertTrue(file.exists());

        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(REPORT_CSV));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expected = List.of("fruit,quantity", "apple,50", "banana,20");
        assertEquals(expected, actual);
    }

    @Test
    void writeReport_emptyData_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileWriter.write(null, REPORT_CSV));
    }
}
