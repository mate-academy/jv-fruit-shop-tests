package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileWriter;
import core.basesyntax.dao.impl.FileReaderImpl;
import core.basesyntax.dao.impl.FileWriterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String NONEXISTENT_FILE = "nonexistentfolder/nonexistentfile.csv";
    private FileWriter fileWriter;
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void write_toCorrectFile_ok() {
        String expectedContent = "BALANCE,apple,100, SUPPLY,banana,50";
        String filePath = "src/test/validfile.csv";
        fileWriter.write(expectedContent, filePath);

        List<String> result = fileReader.read(filePath);
        String joinedResult = String.join(", ", result);
        assertEquals("BALANCE,apple,100, SUPPLY,banana,50", joinedResult);
    }

    @Test
    void write_toNonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileWriter
                .write("BALANCE,apple,100, SUPPLY,banana,50",
                        NONEXISTENT_FILE));
    }
}
