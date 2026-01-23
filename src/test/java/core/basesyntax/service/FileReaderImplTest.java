package core.basesyntax.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReaderImplTest {
    private FileReader fileReader;
    private static final String FILE_PATH = "src/test/inputdata.csv";

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        Files.writeString(Path.of(FILE_PATH), "operation,fruit,quantity\nb,apple,20\ns,banana,10");
    }

    @AfterEach
    void delete() throws IOException {
        Files.deleteIfExists(Path.of("src/test/inputdata.csv"));
    }

    @Test
    void read_correctFile_ok() {
        List<String> dataFromFile = fileReader.readReport(FILE_PATH);

        assertEquals(3, dataFromFile.size());
        assertEquals("operation,fruit,quantity", dataFromFile.get(0));
        assertEquals("b,apple,20", dataFromFile.get(1));
        assertEquals("s,banana,10", dataFromFile.get(2));
    }

    @Test
    void read_nullFilePath_throwException() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readReport(null);
        });
    }

    @Test
    void read_cannotReadFromFile_throwException() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readReport("");
        });
    }

}
