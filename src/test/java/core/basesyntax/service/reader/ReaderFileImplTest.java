package core.basesyntax.service.reader;

import core.basesyntax.model.FruitTransaction;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderFileImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/output.csv";
    private static final String NEGATIVE_FRUIT = "src/main/resources/negativeFruit.csv";
    private ReaderFile readerFile;
    private File file;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        readerFile = new ReaderFileImpl();
        file = new File(INPUT_FILE);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void fileIsExist() {
        Assertions.assertNotNull(file);
    }

    @Test
    void fileIsNotEmpty() {
        String fileContent = String.valueOf(readerFile.readFile(INPUT_FILE));
        Assertions.assertFalse(fileContent.isEmpty());
    }

    @Test
    void readFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String fileContent = String.valueOf(readerFile.readFile("output.scv"));
            Assertions.assertFalse(fileContent.isEmpty());
        });
    }

    @Test
    void fileIsNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(OUTPUT_FILE);
        });
    }

    @Test
    void quantityLessZero_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(NEGATIVE_FRUIT);
        });
    }

    @Test
    void fileReadWithoutMistakes_ok() {
        Assertions.assertDoesNotThrow(() -> {
            readerFile.readFile(INPUT_FILE);
        });
    }
}
