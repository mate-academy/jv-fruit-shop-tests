package core.basesyntax;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String TEST_FILE = "src\\test\\resources\\testFile.csv";
    private static final String UNEXISTING_FILE = "src\\test\\resources\\unexistingFile.csv";
    private FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void fileReader_read_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,50");
        expected.add("s,apple,20");
        expected.add("p,banana,10");
        expected.add("r,banana,13");
        List<String> actual = fileReader.read(TEST_FILE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void fileReader_read_unexist_file_not_ok() {
        Assertions.assertThrows(RuntimeException.class,() -> {
            List<String> actual = fileReader.read(UNEXISTING_FILE);
        });
    }

    @Test
    void fileReader_read_nullPath_not_ok() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read(null);
        });
    }
}
