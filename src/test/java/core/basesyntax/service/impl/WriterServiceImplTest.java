package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.interfaces.WriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterService writerService;

    @BeforeEach
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_OK() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,100" + System.lineSeparator()
                + "b,apple,50" + System.lineSeparator()
                + "s,banana,10";
        String reportFilePath = "src/test/resources/test_report.txt";
        writerService.writeToFile(reportFilePath, expected);
        String actual = readFile(reportFilePath);
        assertEquals(expected, actual.trim(),
                "Data written to file incorrectly!");
    }

    private String readFile(String filepath) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = reader.readLine();
            while (line != null) {
                output.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Oops! File not found: " + filepath, e);
        }
        return output.toString();
    }
}
