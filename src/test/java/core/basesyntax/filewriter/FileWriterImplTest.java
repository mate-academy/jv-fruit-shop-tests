package core.basesyntax.filewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String VALID_FILENAME = "finalReport.csv";
    private static final String STRING_TO_WRITE = "Some string";
    private static FileWriter fw;

    @BeforeAll
    static void beforeAll() {
        fw = new FileWriterImpl();
    }

    @Test
    void writeToFile_validArgs_ok() {
        Path filePath = Path.of(VALID_FILENAME);
        List<String> list;

        fw.writeToFile(STRING_TO_WRITE, VALID_FILENAME);

        try {
            list = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + VALID_FILENAME, e);
        }

        assertEquals(STRING_TO_WRITE, list.get(0));
    }

    @Test
    void writeToFile_NullFilename_NotOk() {
        assertThrows(RuntimeException.class, () -> fw.writeToFile(STRING_TO_WRITE, null));
    }

    @Test
    void writeToFile_NullData_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> fw.writeToFile(null, VALID_FILENAME));
    }
}
