package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.io.FileReader;
import core.basesyntax.io.FileReaderImpl;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String INPUT_VALID =
            "src/test/java/core/basesyntax/resources/FileReaderValidTest.csv";
    private static final String INPUT =
            "src/test/java/core/basesyntax/resources/FileReaderTest.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void fileReader_ReadFromFile_Ok() {
        List<String> expected = List.of("b,banana,100", "p,apple,100");
        List<String> result = fileReader.reader(INPUT);

        assertEquals(expected, result);
    }

    @Test
    void fileReader_ThrowsExceptionIfNotFindFiles_Ok() {
        String input = "wrong/location";

        assertThrows(RuntimeException.class, () -> fileReader.reader(input));
    }

    @Test
    void fileReader_ThrowExceptionWhenFileIsCorrupted_Ok() throws IOException {
        assertThrows(RuntimeException.class, () -> fileReader.reader(INPUT_VALID));
    }
}
