package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    public static final String READ_PATH_INPUT = "src/test/resources/test_input.csv";
    public static final String READ_PATH_EMPTY = "src/test/resources/empty_test_file.csv";
    private static ReaderServiceImpl readerService;
    
    @BeforeAll
    static void setUp() {
        readerService = new ReaderServiceImpl();
    }
    
    @Test
    void fileIsNull_notOk() {
        assertThrows(
                IllegalArgumentException.class, () -> readerService.readTheReport(null));
    }
    
    @Test
    void fileIsEmpty_notOk() {
        assertThrows(IllegalArgumentException.class, () -> readerService.readTheReport(""));
    }
    
    @Test
    void fileRead_isOk() throws IOException {
        List<String> expected = List.of("type,fruit,quantity", "    b,banana,20", "    b,apple,100",
                "    s,banana,100", "    p,banana,13", "    r,apple,10", "    p,apple,20", "    "
                        + "p,banana,5", "    s,banana,50");
        
        List<String> actual = readerService.readTheReport(READ_PATH_INPUT);
        
        assertEquals(expected, actual, "The file content should be read correctly");
    }
    
    @Test
    void emptyFile_isOk() {
        List<String> actual = readerService.readTheReport(READ_PATH_EMPTY);
        
        assertTrue(actual.isEmpty(), "Expected an empty list when reading an empty file");
    }
}
