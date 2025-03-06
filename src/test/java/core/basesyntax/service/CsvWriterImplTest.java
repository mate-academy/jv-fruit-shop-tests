package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.CsvWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterImplTest {
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
        String filePath = "src/test/java/testResources/testWrittenFile.csv";
        List<String> list = new ArrayList<>();
        list.add("banana,20");
        list.add("apple,40");
        File file = new File(filePath);
        csvWriter.writeFile(filePath, list);
        List<String> expected = Files.readAllLines(
                Paths.get("src/test/java/testResources/testExpectedFile.csv"));
        List<String> actual = Files.readAllLines(Paths.get(filePath));
        assertEquals(expected, actual);
        file.delete();
    }
}
