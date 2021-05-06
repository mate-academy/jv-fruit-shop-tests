package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String FILE_PATH = "src/test/java/resources/text_result.csv";
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator()
            + "apple,90";
    private static final WriteServiceImpl writeService = new WriteServiceImpl();

    @Test
    public void write_reportToFile_Ok() throws IOException {
        writeService.writeToFile(report, FILE_PATH);
        List<String> expectedList;
        try {
            expectedList = Files.readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file..." + FILE_PATH);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(report);
        writer.close();
        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file..." + FILE_PATH);
        }
        assertEquals(expectedList, actualList);
    }
}
