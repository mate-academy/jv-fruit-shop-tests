package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterToCsvServiceTest {
    private static final String FILE_NAME = "src/test/resources/output.csv";
    private static final String DATA_FOR_WRITING
            = "fruit,quantity\nbanana,152\napple,90";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterToCsvService();
    }

    @Test
    public void write_someData_ok() {
        writerService.createReportAfterDay(FILE_NAME, DATA_FOR_WRITING);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        assertEquals(expected, readData(FILE_NAME));
    }

    private List<String> readData(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file" + fileName, e);
        }
    }
}
