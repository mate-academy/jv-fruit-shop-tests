package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    public void readFile_validFile_ok() {
        List<String> data = fileReader.read("src/test/resources/valid-file.csv");
        assertEquals(3, data.size());
    }
}
