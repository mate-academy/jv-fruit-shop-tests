package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.io.FileReader;
import core.basesyntax.io.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderTest {

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void fileReader_shouldReadFromFile() {

        List<String> expected = List.of("b,banana,100", "p,apple,100");
        String input = "src/test/java/core/basesyntax/resources/FileReaderTest.csv";

        List<String> result = fileReader.reader(input);

        assertEquals(expected, result);
    }

    @Test
    void setFileReader_shouldThrowsExceptionIfNotFindFiles() {

        String input = "wrong/location";
        assertThrows(RuntimeException.class, () -> fileReader.reader(input));
    }
}
