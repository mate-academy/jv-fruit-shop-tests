package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.basesyntax.service.FileReader;
import core.basesyntax.basesyntax.service.impl.FileReaderImpl;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final int EXPECTED_SIZE = 2;

    private static FileReader reader;

    @BeforeAll
    static void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readFile_validFile_ok() {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("test_input.csv");

        List<String> result = reader.readFile(inputStream);

        assertEquals(EXPECTED_SIZE, result.size());
    }

    @Test
    void readFile_nullInput_throwsException() {
        assertThrows(RuntimeException.class,
                () -> reader.readFile(null));
    }

    @Test
    void readFile_emptyFile_ok() {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());

        List<String> result = reader.readFile(inputStream);

        assertTrue(result.isEmpty());
    }
}
