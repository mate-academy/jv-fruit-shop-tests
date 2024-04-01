package core.basesyntax.service.filewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    public static final String filePath = "src/test/resources/output.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void testWriteToFile_Ok() throws IOException {
        List<String> report = List.of("fruit,quantity", "banana,20", "apple,30");
        fileWriter.writeToFile(report, filePath);

        List<String> outputTest = Files.readAllLines(Path.of(filePath));
        assertEquals(report, outputTest);
    }
}
