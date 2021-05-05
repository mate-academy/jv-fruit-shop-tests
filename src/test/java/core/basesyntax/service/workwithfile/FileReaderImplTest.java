package core.basesyntax.service.workwithfile;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String PATH = "src/test/resources/correct_fruit.csv";
    private static final String WRONG_PATH = "asd.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFile_Ok() throws IOException {
        assertEquals(Files.readAllLines(Path.of(PATH)),
                fileReader.readFromFile(PATH));
    }

    @Test (expected = RuntimeException.class)
    public void cannotReadFile_NotOk() {
        fileReader.readFromFile(WRONG_PATH);
    }
}
