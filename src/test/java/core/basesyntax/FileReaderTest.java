package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        String filePath = "src/test/resources/testInput.csv";
        List<String> actualLines = fileReader.read(filePath);
        Assertions.assertNotNull(actualLines);
        Assertions.assertFalse(actualLines.isEmpty());
    }

    @Test
    void read_nonExistFile_notOk() {
        String invalidPath = "src/test/resources/nonExistFile.csv";
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read(invalidPath);
        });
    }

    @Test
    void read_wrongFilePath() {
        String wrongPath = "src/test/resorcec/testInput.csv";
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read(wrongPath);
        });
    }
}
