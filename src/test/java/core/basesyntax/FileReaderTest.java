package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.serviceimpl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_correct() {
        final String testPath = "src/test/java/resources/testInput.csv";
        List<String> data = fileReader.read(testPath);
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertEquals("b,banana,20", data.get(1));
    }

    @Test
    void read_invalidFile_throwsException() {
        final String invalidFilePath = "src/test/java/resources";
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> fileReader.read(invalidFilePath));
        assertEquals("Can't read file from path: " + invalidFilePath, thrown.getMessage());
    }
}
