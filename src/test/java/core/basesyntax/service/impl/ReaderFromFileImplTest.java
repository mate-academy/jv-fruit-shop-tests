package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReaderFromFileImplTest {
    private static final String VALID_INPUT_FILE = "src/main/resources/inputData.csv";
    private static final String EMPTY_FILE = "empty.csv";
    private Reader reader;

    @TempDir
    private Path temporaryDirectory;

    @BeforeEach
    void setUp() {
        reader = new ReaderFromFileImpl();
    }

    @Test
    void correctInputFile_readFromFile_ok() {
        List<String> expectedList = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "s,banana,50");
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
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> reader.readFromFile(invalidInputFile));
        assertEquals("Can not read the file: " + invalidInputFile,
                runtimeException.getMessage());
    }
}
