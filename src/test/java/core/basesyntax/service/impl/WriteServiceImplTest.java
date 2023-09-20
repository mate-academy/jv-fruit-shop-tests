package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.WriteService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteServiceImplTest {
    private static final String PATH_TO_FILE = "src\\resources\\testReport.csv";

    private static WriteService writeService;

    @BeforeAll
    static void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    void writeToFile_writePathToFile_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,40";
        writeService.writeToFile(PATH_TO_FILE, report);
        String actualReport = readFromFile();
        assertEquals(report, actualReport);
    }

    private static String readFromFile() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_FILE))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("You can`t read data from file " + PATH_TO_FILE, e);
        }
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
