package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FileReaderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderServiceTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";

    private static FileReaderService readFromFile;

    @AfterEach
    public void clearStorage() {
        Storage.Storage.clear();
    }

    @BeforeAll
    static void setUp() {
        readFromFile = new FileReaderService();
    }

    @Test
    public void read_validFile_ok() {
        String expectedResult = "type,fruit,quantityb,banana,20b,apple,100s,"
                + "banana,100p,banana,13r,apple,10p,apple,20p,banana,5s,banana,50";
        assertEquals(expectedResult, readFromFile.read(INPUT_FILE));
    }

    @Test
    public void read_invalidInput_notOk() {
        String newInput = "src/main/resources/data.csv";
        assertThrows(RuntimeException.class, () -> readFromFile.read(newInput));
    }
}
