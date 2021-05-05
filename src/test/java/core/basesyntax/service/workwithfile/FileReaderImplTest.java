package core.basesyntax.service.workwithfile;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFile_Ok() throws IOException {
        assertEquals(Files.readAllLines(Path.of("src/test/resources/correct_fruit.csv")),
                fileReader.readFromFile("src/test/resources/correct_fruit.csv"));
    }

    @Test (expected = RuntimeException.class)
    public void cannotReadFile_NotOk() {
        fileReader.readFromFile("asd.csv");
    }
}
