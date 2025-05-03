package core.basesyntax.file.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String CORRECT_PATH = "src/test/java/resources/testFile.csv";
    private static final String INCORRECT_PATH = "src/test/java/resources/invalidPath.csv";
    private ReportFileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_Ok() throws IOException {
        List<String> actual = fileReader.read(CORRECT_PATH);
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,30",
                "s,apple,20");

        assertEquals(expected, actual);
    }

    @Test
    void read_incorrectPath_NotOk() {
        assertThrows(IOException.class, () -> fileReader.read(INCORRECT_PATH));
    }
}
