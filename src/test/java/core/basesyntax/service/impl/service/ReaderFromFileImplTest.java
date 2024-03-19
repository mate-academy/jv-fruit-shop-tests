package core.basesyntax.service.impl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Reader;
import core.basesyntax.service.impl.ReaderFromFileImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReaderFromFileImplTest {
    private static final String VALID_INPUT_FILE = "src/main/resources/inputDataForTest.csv";
    private static final String EMPTY_FILE = "empty.csv";
    private static final List<String> VALID_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "s,banana,50");

    private Reader reader;

    @TempDir
    private Path temporaryDirectory;

    @BeforeEach
    void setUp() {
        reader = new ReaderFromFileImpl();
    }

    @Test
    void correctInputFile_readFromFile_ok() {
        List<String> expectedList = VALID_DATA;
        List<String> actualList = reader.readFromFile(VALID_INPUT_FILE);
        assertEquals(expectedList, actualList);
    }

    @Test
    void emptyFile_readFromFile_ok() throws IOException {
        Path emptyFile = Files.createFile(temporaryDirectory.resolve(EMPTY_FILE));
        List<String> actualLines = reader.readFromFile(emptyFile.toString());
        assertTrue(actualLines.isEmpty(),
                "The read data should be an empty list for an empty file.");
    }

    @Test
    void invalidInputFile_readFromFile_notOk() {
        String invalidInputFile = "src/main/resources/outputDat.csv";
        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> reader.readFromFile(invalidInputFile));
        assertEquals("Can not read the file: " + invalidInputFile,
                actualException.getMessage());
    }
}
