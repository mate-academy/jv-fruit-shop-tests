package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_returnsListOfLines() {
        List<String> output = fileReader.read("src/test/resources/testData.csv");
        assertFalse(output.isEmpty());
    }

    @Test
    void read_emptyFile_returnsEmptyList() {
        List<String> output = fileReader.read("src/test/resources/empty.csv");
        assertTrue(output.isEmpty());
    }

    @Test
    void read_fileNotExists_throwsException() {
        assertThrows(RuntimeException.class, () ->
                fileReader.read("src/test/resources/nonExist.csv"));
    }
}
