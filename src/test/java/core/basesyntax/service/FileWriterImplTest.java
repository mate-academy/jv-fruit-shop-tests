package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String REPORT_TO_WRITE_FILE = "src/test/resources/finalReportCorrect.csv";
    private static FileWriterImpl fileWriter;
    private static final String TEXT_TO_WRITE = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,990";

    @BeforeAll
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeReportToFile_Ok() {
        String[] textArray = TEXT_TO_WRITE.split(System.lineSeparator());
        Path path = Paths.get(REPORT_TO_WRITE_FILE);
        fileWriter.write(TEXT_TO_WRITE, path);
        try {
            List<String> expected = new ArrayList<>();
            List<String> actual = new ArrayList<>();
            for (int i = 0; i < textArray.length; i++) {
                expected.add(i, Files.readAllLines(path).get(i));
                actual.add(i, textArray[i]);
            }
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file at path: " + path, e);
        }
    }
}
