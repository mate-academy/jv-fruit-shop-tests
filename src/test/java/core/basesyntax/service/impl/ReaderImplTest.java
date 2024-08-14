package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private Reader reader = new ReaderImpl();

    @Test
    void read_ok() {
        List<String> expected = List.of("Q", "q", "1", "!","ы", "і");
        assertEquals(expected, reader.read("src/test/resources/InputFile.csv"),
                "Reader get wrong information");
    }

    @Test
    void read_nonExistFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read("src/test/resources/NonExistFile"),
                "Reader should throw exception");
    }
}
