package core.basesyntax.file.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileManagerImplTest {
    private static FileManagerImpl fileManager;

    @BeforeAll
    static void setUp() {
        fileManager = new FileManagerImpl();
    }

    @Test
    void read_fileNotFound_throwsException() {
        String inputFile = "fake.csv";

        assertThrows(RuntimeException.class, () -> {
            fileManager.read(inputFile);
        });
    }

    @Test
    void read_nullValue_throwsException() {
        String inputFile = null;

        assertThrows(IllegalArgumentException.class, () -> {
            fileManager.read(inputFile);
        });
    }

    @Test
    void read_validInputData_ok() {
        String pathFolder =
                "src" + File.separator + "main" + File.separator + "resources" + File.separator;
        List<String[]> inputData = fileManager.read(pathFolder + "input.csv");

        assertEquals(8, inputData.size());
        assertArrayEquals(new String[]{"b", "banana", "20"}, inputData.get(0));
    }

    @Test
    void write_validData_ok() throws IOException {
        Path tempFile = Files.createTempFile("testOutput", ".csv");

        List<String[]> report = List.of(
                new String[]{"banana", "50"},
                new String[]{"apple", "30"}
        );

        fileManager.write(report, tempFile.toString());
        List<String> lines = Files.readAllLines(tempFile);

        assertEquals(3, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("banana,50", lines.get(1));
        assertEquals("apple,30", lines.get(2));
    }
}
