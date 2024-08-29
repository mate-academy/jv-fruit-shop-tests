package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReaderImplTest {
    private static final String FILES_ROOT = "src/test/resources/csv_reader/";
    private static CsvReader reader;

    @BeforeAll
    static void beforeAll() {
        reader = new CsvReaderImpl();
    }

    @Test
    void readLines_validFile_ok() {
        List<String> expected = List.of("test,file,to",
                "check,if,reader",
                "can,actually,read");
        List<String> actual = reader.readLines(FILES_ROOT + "read_ok.csv");
        assertEquals(3, actual.size(), "Expected 3 lines to be read");
        assertEquals(expected, actual, "Expected lines to be equal");
    }

    @Test
    void readLines_singleLine_ok() {
        List<String> lines = reader.readLines(FILES_ROOT + "read_single_line.csv");
        assertEquals(0, lines.size(), "Expected 0 lines to be read");
    }

    @Test
    void readLines_empty_ok() {
        List<String> lines = reader.readLines(FILES_ROOT + "read_empty.csv");
        assertEquals(0, lines.size(), "Expected 0 lines to be read");
    }

    @Test
    void readLines_anotherFormat_ok() {
        List<String> lines = reader.readLines(FILES_ROOT + "read_another_format.txt");
        assertEquals(6, lines.size(), "Expected 6 lines to be read");
    }

    @Test
    void readLines_wrongFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader
                        .readLines(FILES_ROOT + "read_wrong.csv"),
                "Reading a non-existent file should throw an exception");
    }
}
