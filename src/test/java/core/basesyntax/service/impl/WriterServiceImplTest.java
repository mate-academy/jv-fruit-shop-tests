package core.basesyntax.service.impl;

import core.basesyntax.service.interfaces.WriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
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
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,100" + System.lineSeparator()
                + "b,apple,50" + System.lineSeparator()
                + "s,banana,10";
        StringBuilder output = new StringBuilder();
        String reportFilePath = "src/test/resources/test_report.txt";
        writerService.writeToFile(reportFilePath, input);
        try (BufferedReader reader = new BufferedReader(new FileReader(reportFilePath))) {
            String line = reader.readLine();
            while (line != null) {
                output.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
            Assertions.assertEquals(input, output.toString().trim(),
                    "Data written to file incorrectly!");
        } catch (IOException e) {
            throw new RuntimeException("Oops! File not found: " + reportFilePath, e);
        }
    }
}
