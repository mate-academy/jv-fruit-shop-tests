package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ReadFileException;
import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String INPUT_PATH = "src/main/resources/input.csv";
    private final FileReader reader = new FileReaderImpl();

    @Test
    void readFrom_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> inputLines = reader.readFrom(INPUT_PATH);
        assertEquals(expected, inputLines);
    }

    @Test
    void readFrom_null_notOk() {
        ReadFileException existsException = assertThrows(
                ReadFileException.class,
                () -> reader.readFrom(null)
        );
        assertEquals("Can't read file " + null,
                existsException.getMessage());
    }
}
