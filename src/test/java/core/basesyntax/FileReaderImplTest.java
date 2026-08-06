package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    @Test
    void read_validFile_returnsListOfLines() {
        FileReader fileReader = new FileReaderImpl();
        List<String> output = fileReader.read("src/test/resources/testData.csv");
        Assertions.assertFalse(output.isEmpty());
    }

    @Test
    void read_emptyFile_returnsEmptyList() {
        FileReader fileReader = new FileReaderImpl();
        List<String> output = fileReader.read("src/test/resources/empty.csv");
        Assertions.assertTrue(output.isEmpty());
    }

    @Test
    void read_fileNotExists_throwsException() {
        FileReader fileReader = new FileReaderImpl();
        assertThrows(RuntimeException.class, () ->
                fileReader.read("src/test/resources/nonExist.csv"));
    }
}
