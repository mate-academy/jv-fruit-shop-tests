package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFile_validFile_Ok() {
        List<String> actual = fileReader.readFile("src/test/resources/reportToRead.csv");

        assertFalse(actual.isEmpty());
        assertEquals("type,fruit,quantity", actual.get(0));
    }

    @Test
    public void readFile_emptyFile_Ok() {
        List<String> actual = fileReader.readFile("src/test/resources/emptyReport.csv");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void readFile_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReader.readFile("src/test/resources/emptyReport2.csv"));
    }
}
