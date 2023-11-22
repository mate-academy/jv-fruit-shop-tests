package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static String VALID_OUTPUT_FILE;

    @BeforeClass
    public static void setUp() {
        VALID_OUTPUT_FILE = "src/test/resources/output.csv";
    }

    @Test
    public void writeToFile_Ok() {
        WriterService writerService = new WriterServiceImpl();
        StringBuilder expectedStringBuilder = new StringBuilder("fruit,quantity");
        String actualString;
        writerService.writeToFile(VALID_OUTPUT_FILE, expectedStringBuilder.toString());
        actualString = checkActualData(VALID_OUTPUT_FILE);
        assertEquals(expectedStringBuilder.toString(), actualString);

        expectedStringBuilder
                .append(System.lineSeparator())
                .append("banana,10");
        writerService.writeToFile(VALID_OUTPUT_FILE, expectedStringBuilder.toString());
        actualString = checkActualData(VALID_OUTPUT_FILE);
        assertEquals(expectedStringBuilder.toString(), actualString);
    }

    private String checkActualData(String path) {
        List<String> actualData;
        try {
            actualData = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + path, e);
        }
        return actualData.stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
