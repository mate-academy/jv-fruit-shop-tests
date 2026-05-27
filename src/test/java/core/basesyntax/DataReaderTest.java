package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataReader;
import core.basesyntax.service.impl.DataReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataReaderTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/Correct.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/Empty.csv";
    private static final DataReader READER = new DataReaderImpl();
    private static final List<String> CORRECT_FILE_CONTENT = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CORRECT_FILE_CONTENT.add("type,fruit,quantity");
        CORRECT_FILE_CONTENT.add("b,banana,20");
        CORRECT_FILE_CONTENT.add("b,apple,100");
    }

    @Test
    void dataReaderTest_correctFile_ok() {
        List<String> actual = READER.read(CORRECT_FILE_PATH);
        assertEquals(CORRECT_FILE_CONTENT, actual);
    }

    @Test
    void dataReaderTest_emptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> READER.read(EMPTY_FILE_PATH));
    }
}
