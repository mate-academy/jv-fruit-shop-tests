package core.basesyntax.file.writer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private static final String CSV_EXTENSION = ".csv";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("output", CSV_EXTENSION);

        String data = HEADER + "\n"
                + "b," + FRUIT_BANANA + ",20\n"
                + "s," + FRUIT_APPLE + ",30";

        fileWriter.write(data, tempFile.toString());

        List<String> writtenData = Files.readAllLines(tempFile);

        System.out.println("Written data: " + writtenData);

        assertTrue(writtenData.contains(HEADER));
        assertTrue(writtenData.contains("b," + FRUIT_BANANA + ",20"));
        assertTrue(writtenData.contains("s," + FRUIT_APPLE + ",30"));

        Files.delete(tempFile);
    }
}
