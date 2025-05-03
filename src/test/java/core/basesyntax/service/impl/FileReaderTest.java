package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String TEST_FILE = "src/test/resources/testFile.csv";
    private static final String UNEXISTING_FILE = "src/test/resources/unexistingFile.csv";
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validValue_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,50",
                "s,apple,20",
                "p,banana,10",
                "r,banana,13"
        );
        List<String> actual = fileReader.read(TEST_FILE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void read_unexistFile_notOk() {
        Assertions.assertThrows(RuntimeException.class,() -> {
            List<String> actual = fileReader.read(UNEXISTING_FILE);
        });
    }

    @Test
    void read_nullPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read(null);
        });
    }
}
