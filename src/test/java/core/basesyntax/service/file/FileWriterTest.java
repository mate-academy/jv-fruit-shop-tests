package core.basesyntax.service.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static FileWriter fileWriter;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFIle_correctWrite_Ok() {
        fileWriter.writeToFile("src/test/resources/writeTest.csv", "Still hate Junit4");
        File file = new File("src/test/resources/writeTest.csv");
        try {
            List<String> actual = Files.readAllLines(file.toPath());
            expected = List.of("Still hate Junit4");
            assertEquals(expected, actual);
            Files.write(file.toPath(), "".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
    }
}
