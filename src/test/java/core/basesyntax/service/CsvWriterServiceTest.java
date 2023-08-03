package core.basesyntax.service;

import core.basesyntax.service.impl.CsvWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterServiceTest {
    private static final String VALID_OUTPUT_PATH = "src/test/resources/validTestReport.csv";
    private static final String VALID_INPUT_STRING = "fruit,quantity" + System.lineSeparator()
            + "orange, 243";
    private WriterService csvWriterService;

    @BeforeEach
    void setUp() {
        csvWriterService = new CsvWriterService();
    }

    @Test
    void write_validData_ok() {
        csvWriterService.write(VALID_INPUT_STRING, VALID_OUTPUT_PATH);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "orange, 243";
        String actual = readFromCsv(VALID_OUTPUT_PATH);
        Assertions.assertEquals(expected, actual,
                "Report should be the same as written!");
    }

    public String readFromCsv(String filePath) {
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file: " + filePath, e);
        }
    }
}
