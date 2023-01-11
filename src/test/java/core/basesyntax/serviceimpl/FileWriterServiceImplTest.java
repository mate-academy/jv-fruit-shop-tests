package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String PATH_TO_WRITE_FILE = "src/test/resources/write_file.csv";
    private static final String STRING_TO_WRITE = "type,fruit,quantity" + "\n"
            + "banana,152" + "\n" + "apple,90";
    private WriterService writerService;

    @Before
    public void setUp() {

        writerService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "banana,152", "apple,90");
        writerService.writeToFile(STRING_TO_WRITE, PATH_TO_WRITE_FILE);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_WRITE_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + PATH_TO_WRITE_FILE);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToNotExistingPath() {
        writerService.writeToFile(STRING_TO_WRITE, "");
    }
}
