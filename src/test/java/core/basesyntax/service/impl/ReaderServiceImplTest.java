package core.basesyntax.service.impl;

import static core.basesyntax.constants.Constants.CORRECT_PATH_TO_READ;
import static core.basesyntax.constants.Constants.INCORRECT_PATH_TO_READ;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private final ReaderService fileReader = new ReaderServiceImpl();

    @Test
    void read_validFile_ok() {
        List<String> expectedContent = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,1",
                "b,apple,1");
        assertEquals(expectedContent, fileReader.read(CORRECT_PATH_TO_READ));
    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReader.read(INCORRECT_PATH_TO_READ));
    }
}
