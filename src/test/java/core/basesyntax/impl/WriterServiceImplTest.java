package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static List<String> data;
    private static String outputPath;

    @BeforeAll
    static void setup() {
        writerService = new WriterServiceImpl();
        data = new ArrayList<>();
        outputPath = new String("/src/main/java/core/recourses/report.csv");
    }

    @Test
    void writeToFile_validDataAndPath() throws IOException {
        List<String> data = Arrays.asList("Line 1", "Line 2", "Line 3");
        String outputPath = "output.txt";

        writerService.writeToFile(data, outputPath);

        List<String> lines = Files.readAllLines(Path.of(outputPath));
        assertEquals(data, lines);
    }

    @Test
    void writeToFile_emptyData_throwsException() {
        List<String> emptyData = Arrays.asList();
        String outputPath = "output.txt";

        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(emptyData, outputPath);
        });
    }

    @Test
    void writeToFile_emptyPath_throwsException() {
        List<String> data = Arrays.asList("Line 1", "Line 2", "Line 3");
        String emptyPath = "";

        assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile(data, emptyPath);
        });
    }
}
