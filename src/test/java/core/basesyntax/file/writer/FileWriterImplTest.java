package core.basesyntax.file.writer;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String FILE_TEST = "src/test/resources/report/report.csv";
    private static final String FILE_CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeTest_Ok() throws IOException {
        fileWriter.write(FILE_TEST, FILE_CONTENT);

        List<String> expectedList;
        try {
            expectedList = Files.readAllLines(Path.of(FILE_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file " + FILE_TEST);
        }

        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(FILE_TEST));
        writer.write(FILE_CONTENT);
        writer.close();

        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(FILE_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + FILE_TEST);
        }

        assertEquals(expectedList, actualList);
    }
}
