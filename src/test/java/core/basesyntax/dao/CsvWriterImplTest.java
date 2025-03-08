package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterImplTest {
    private static final String ACTUAL_PATH = "src/test/java/testResources/testWrittenFile.csv";
    private CsvWriterImpl csvWriter;

    @BeforeEach
    void setUp() {
        csvWriter = new CsvWriterImpl();
    }

    @Test
    void file_IsNull_NotOk() {
        String filePath = null;
        List<String> list = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            csvWriter.writeFile(filePath, list);
        });
    }

    @Test
    void file_checkOutput_IsOk() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("banana,20");
        list.add("apple,40");
        csvWriter.writeFile(ACTUAL_PATH, list);
        List<String> expected = new ArrayList<>();
        expected.add("banana,20");
        expected.add("apple,40");
        List<String> actual = Files.readAllLines(Paths.get(ACTUAL_PATH));
        assertEquals(expected, actual);
    }
}
