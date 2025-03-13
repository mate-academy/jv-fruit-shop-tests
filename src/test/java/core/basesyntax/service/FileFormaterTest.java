package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import core.basesyntax.dao.CsvReaderImpl;
import core.basesyntax.dao.CustomFileReader;
import core.basesyntax.service.impl.FileFormaterForCsvReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileFormaterTest {
    private static final String FILE_PATH = "src/test/java/testResources/testFormatterFile.csv";
    private static CustomFileReader reader;
    private static FileFormaterForCsvReader fileFormaterForCsvReader;

    @BeforeAll
    static void set_Up() {
        reader = new CsvReaderImpl();
        fileFormaterForCsvReader = new FileFormaterForCsvReader(reader);
    }

    @Test
    void file_Parsing_IsOk() {
        List<String[]> result = fileFormaterForCsvReader.parseCsv(FILE_PATH);
        assertArrayEquals(new String[]{"operation", "fruit", "quantity"}, result.get(0));
        assertArrayEquals(new String[]{"s", "apple", "20"}, result.get(1));
    }
}
