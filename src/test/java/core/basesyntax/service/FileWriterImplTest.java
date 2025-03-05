package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String REPORT_TO_WRITE_FILE = "src/test/resources/finalReportCorrect.csv";
    private static FileWriterImpl fileWriter;
    private static final String textToWrite = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,990";

    @BeforeAll
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeReportToFile_Ok() {
        String[] fileArray = textToWrite.split(System.lineSeparator());
        fileWriter.write(textToWrite, Path.of(REPORT_TO_WRITE_FILE));
        try {
            String firstLine = Files.readAllLines(Path.of(REPORT_TO_WRITE_FILE)).get(0);
            String secondLine = Files.readAllLines(Path.of(REPORT_TO_WRITE_FILE)).get(1);
            String thirdLine = Files.readAllLines(Path.of(REPORT_TO_WRITE_FILE)).get(2);
            assertEquals(fileArray[0], firstLine);
            assertEquals(fileArray[1], secondLine);
            assertEquals(fileArray[2], thirdLine);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
    }
}
