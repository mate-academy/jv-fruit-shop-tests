package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderTest {

    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() {
        String filePath = "src/test/resources/testInput.csv";
        List<String> actualLines = fileReader.read(filePath);
        assertNotNull(actualLines);
        assertFalse(actualLines.isEmpty());
    }

    @Test
    void read_nonExistFile_notOk() {
        String invalidPath = "src/test/resources/nonExistFile.csv";
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(invalidPath);
        });
    }

    @Test
    void read_wrongFilePath() {
        String wrongPath = "src/test/resorcec/testInput.csv";
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(wrongPath);
        });
    }
}
