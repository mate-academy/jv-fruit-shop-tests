package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitDataReaderService;
import core.basesyntax.service.DataReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitDataReaderServiceTest {
    private static final String FILE_EXIST_PATH = "src/test/resources/existingFile";
    private static final String FILE_DOES_NOT_EXIST_PATH = "src/test/resources/data_not_exist.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile";
    private static DataReaderService dataReader;

    @BeforeAll
    static void beforeAll() {
        dataReader = new FruitDataReaderService();
    }

    @Test
    void read_ValidFile_Ok() {
        List<String> actual = dataReader.read(FILE_EXIST_PATH);
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test
    void read_NonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () ->
                        dataReader.read(FILE_DOES_NOT_EXIST_PATH),
                "Can't find the file with this pathname: src/test/resources/data_not_exist.csv");
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList() {
        List<String> actual = dataReader.read(EMPTY_FILE_PATH);
        List<String> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }
}
