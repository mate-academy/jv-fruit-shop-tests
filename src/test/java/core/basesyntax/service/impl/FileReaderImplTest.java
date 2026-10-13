package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_existingFile_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "s,apple,10");

        List<String> actual = fileReader.read("src/test/resources/input.csv");

        assertEquals(expected, actual);
    }

    @Test
    void read_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("src/test/resources/missing.csv"));
    }
}
