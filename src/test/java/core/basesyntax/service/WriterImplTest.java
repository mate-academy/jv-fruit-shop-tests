package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.WrongFileType;
import core.basesyntax.service.impl.WriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterImplTest {
    private static final String INVALID_FILE_NAME = "src/main/invalid/invalid";
    private static final String WRONG_TYPE_FILE_NAME = "src/test/resources/wrongTypeFileName.css";
    private static final String VALID_FILE_NAME = "src/test/resources/testRecord.csv";
    private static final Map<String, Integer> MAP_TO_WRITE = Map.of("apple", 20,"banana", 10);
    private static Writer writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterImpl(MAP_TO_WRITE);
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
    public void write_validFileName_ok() throws IOException {
        List<String> expected = Arrays.asList("apple,20", "banana,10");
        writer.write(VALID_FILE_NAME);
        List<String> actual = Files.readAllLines(Paths.get(VALID_FILE_NAME))
                .stream().skip(1).collect(Collectors.toList());
        assertEquals(expected, actual);
    }
}
