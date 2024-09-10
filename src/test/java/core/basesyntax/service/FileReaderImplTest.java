package core.basesyntax.service;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String INPUT_FILE = "src/main/java/resources/reportToRead.csv";
    private static final String NON_EXISTENT_FILE = "src/main/java/resources/non_existent_file.csv";
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_ReturnCorrectLines_Ok() {
        List<String> expected = Stream.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50").toList();
        List<String> actual = fileReader.read(INPUT_FILE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void read_NonExistentFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(NON_EXISTENT_FILE));
    }

    @Test
    void read_NullFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(null));
    }
}
