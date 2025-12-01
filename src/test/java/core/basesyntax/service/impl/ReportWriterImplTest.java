package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReportWriterImplTest {
    private ReportWriter writer;
    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        writer = new ReportWriterImpl();
    }

    @Test
    void write_validReport_ok() throws IOException {
        Path testFile = tempDir.resolve("output.csv");
        String report = "fruit,quantity" + System.lineSeparator() + "apple,100";
        writer.write(report, testFile.toString());
        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("apple,100", lines.get(1));
    }

    @Test
    void write_emptyReport_ok() throws IOException {
        Path testFile = tempDir.resolve("empty.csv");
        String report = "";
        writer.write(report, testFile.toString());
        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(0, lines.size());
    }

    @Test
    void write_multilineReport_ok() throws IOException {
        Path testFile = tempDir.resolve("multiline.csv");
        String report = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,75";
        writer.write(report, testFile.toString());
        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(4, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("apple,100", lines.get(1));
        assertEquals("banana,50", lines.get(2));
        assertEquals("orange,75", lines.get(3));
    }

    @Test
    void write_createsDirectories_ok() throws IOException {
        Path subDir = tempDir.resolve("subdir").resolve("nested");
        Path testFile = subDir.resolve("output.csv");
        String report = "fruit,quantity";
        writer.write(report, testFile.toString());
        assertTrue(Files.exists(testFile));
        assertTrue(Files.exists(subDir));
    }

    @Test
    void write_overwritesExistingFile_ok() throws IOException {
        Path testFile = tempDir.resolve("existing.csv");
        Files.write(testFile, "old content".getBytes());
        String report = "fruit,quantity" + System.lineSeparator() + "apple,100";
        writer.write(report, testFile.toString());
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("apple,100", lines.get(1));
    }

    @Test
    void write_utf8Characters_ok() throws IOException {
        Path testFile = tempDir.resolve("utf8.csv");
        String report = "fruit,quantity" + System.lineSeparator() + "яблуко,100";
        writer.write(report, testFile.toString());
        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("яблуко,100", lines.get(1));
    }

    @Test
    void write_headerOnly_ok() throws IOException {
        Path testFile = tempDir.resolve("headerOnly.csv");
        String report = "fruit,quantity";
        writer.write(report, testFile.toString());
        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
    }

    @Test
    void write_largeReport_ok() throws IOException {
        Path testFile = tempDir.resolve("large.csv");
        StringBuilder report = new StringBuilder("fruit,quantity");
        for (int i = 0; i < 1000; i++) {
            report.append(System.lineSeparator()).append("fruit").append(i).append(",").append(i);
        }
        writer.write(report.toString(), testFile.toString());
        assertTrue(Files.exists(testFile));
        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1001, lines.size());
    }
}
