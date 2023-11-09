package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static FileReader fileReader = new CsvFileReader();

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvFileReader();
    }

    @Test
    void read_ValidData_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.read("inputData.csv");
        assertEquals(expected, actual);
    }

    @Test
    void read_ValidData2_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,strawberry,250",
                "b,mango,56",
                "b,lemon,10",
                "p,mango,9",
                "r,lemon,15",
                "s,strawberry,50",
                "r,mango,6");
        List<String> actual = fileReader.read("inputDataTwo.csv");
        assertEquals(expected, actual);
    }

    @Test
    void read_FileIsNull_NotOk() {
        String fileName = null;
        assertThrows(RuntimeException.class, () -> fileReader.read(fileName),
                "Can't read from file fileName");
    }

    @Test
    void read_FileIsEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read("empty.csv"),
                "Can't read from file fileName");
    }
}
