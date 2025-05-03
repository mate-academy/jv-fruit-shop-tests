package core.basesyntax.service.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.EmptyStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private final Storage storage = new Storage();
    private final ReportGenerator generator = new ReportGenerator();

    @BeforeEach
    void setUp() {
        storage.getData().clear();
    }

    @Test
    public void generate_correctInformation_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        storage.getData().put("banana", 152);
        storage.getData().put("apple", 90);

        System.out.println(expected);
        String actual = generator.generate(storage);
        assertEquals(expected, actual);
    }

    @Test
    public void generate_withEmptyStorage_NotOk() {
        EmptyStorageException exception = assertThrows(EmptyStorageException.class,
                () -> generator.generate(storage));
        assertEquals("Storage shouldn't be empty", exception.getMessage(),
                "Incorrect validation error message");
    }
}
