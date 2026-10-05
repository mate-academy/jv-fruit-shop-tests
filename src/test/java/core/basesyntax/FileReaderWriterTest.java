package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderWriterTest {

    @Test
    void readAndWrite_validFile_ok(@TempDir File tempDir) {
        File file = new File(tempDir, "test.csv");
        FileWriter fileWriter = new FileWriterImpl();
        FileReader fileReader = new FileReaderImpl();
        String content = "type,fruit,quantity" + System.lineSeparator() + "b,banana,20";
        fileWriter.write(content, file.getAbsolutePath());
        List<String> actualLines = fileReader.read(file.getAbsolutePath());
        assertEquals(2, actualLines.size());
        assertEquals("type,fruit,quantity", actualLines.get(0));
    }

    @Test
    void read_invalidPath_notOk() {
        FileReader fileReader = new FileReaderImpl();
        assertThrows(RuntimeException.class,
                () -> fileReader.read("invalid/path/file.csv"));
    }

    @Test
    void read_emptyFile_ok(@TempDir File tempDir) {
        File file = new File(tempDir, "empty.csv");
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write("", file.getAbsolutePath());
        FileReader fileReader = new FileReaderImpl();
        List<String> actualLines = fileReader.read(file.getAbsolutePath());
        assertTrue(actualLines.isEmpty());
    }
}
