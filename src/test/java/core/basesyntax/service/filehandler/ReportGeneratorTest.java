package core.basesyntax.service.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.EmptyStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private Storage storage;
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ReportGenerator();
        storage = new Storage();
        storage.getData().clear();
    }

    @Test
    public void generate_CorrectInformation_Ok() {
        String expected = String.format("fruit,quantity%nbanana,152%napple,90");
        storage.getData().put("banana", 152);
        storage.getData().put("apple", 90);

        String actual = generator.generate(storage);
        assertEquals(expected, actual);
    }

    @Test
    public void generate_WithEmptyStorage_NotOk() {
        EmptyStorageException exception = assertThrows(EmptyStorageException.class,
                () -> generator.generate(storage));
        assertEquals("Storage shouldn't be empty", exception.getMessage(),
                "Incorrect validation error message");
    }
}
