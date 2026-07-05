package core.basesyntax.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void read_validFile_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,20"
        );
        List<String> actual = reader.read("src/test/resources/testRead.csv");
        assertEquals(expected, actual);

    }

    @Test
    void read_invalidFile_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read("src/test/resources/test.csv"));
        String expectedMessage = "Can't read file by path: src/test/resources/test.csv";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
