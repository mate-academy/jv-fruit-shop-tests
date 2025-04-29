package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private FileReaderImpl fileReader;
    private String falidPath = "src\\test\\java\\core\\basesyntax\\resources\\databaseTest.csv";
    private String invalidPath = "invalidPath";

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validPath_ok() {
        List<String> actual = fileReader.read(falidPath);

        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100"
        );

        assertEquals(expected, actual);
    }

    @Test
    void read_invalidPath_throwsFileNotFoundException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(invalidPath));
    }

    @Test
    void read_nullInput_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(null));
    }
}
