package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private final Reader reader = new ReaderImpl();

    @Test
    void read_nullValue_notOk() {
        assertThrows(RuntimeException.class, () -> {
            List<String> data = reader.readFromFile(null);
        });
    }

    @Test
    void read_nonExistFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            List<String> data =
                    reader.readFromFile("src/main/java/core/basesyntax/db/nonExistSource.csv");
        });
    }

    @Test
    void read_existFile_ok() {
        List<String> data = reader.readFromFile("src/main/java/core/basesyntax/db/source.csv");
        assertTrue(data != null);
    }
}
