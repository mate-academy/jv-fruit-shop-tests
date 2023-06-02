package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.WrongFileType;
import core.basesyntax.service.impl.ReaderImpl;
import core.basesyntax.service.impl.WriterImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterImplTest {
    private static final String INVALID_FILE_NAME = "src/main/invalid/invalid";
    private static final String WRONG_TYPE_FILE_NAME = "src/main/resources/shortTestData.css";
    private static final String VALID_FILE_NAME = "src/main/resources/testRecord.csv";
    private static final Map<String, Integer> mapToWrite = Map.of("banana", 10, "apple", 20);
    private static Writer writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterImpl(mapToWrite);
    }

    @Test
    public void writer_nullFileName_notOk() {
        assertThrows(NullPointerException.class, () ->
                writer.write(null));
    }

    @Test
    public void write_invalidFileName_notOk() {
        assertThrows(RuntimeException.class, () ->
                writer.write(INVALID_FILE_NAME));
    }

    @Test
    public void write_wrongTypeFileName_notOk() {
        assertThrows(WrongFileType.class, () ->
                writer.write(WRONG_TYPE_FILE_NAME));
    }

    @Test
    public void write_validFileName_ok() {
        List<String> expected = List.of("apple,20", "banana,10");
        writer.write(VALID_FILE_NAME);
        Reader reader = new ReaderImpl();
        List<String> actual = reader.read(VALID_FILE_NAME);
        assertEquals(expected, actual);
    }
}
