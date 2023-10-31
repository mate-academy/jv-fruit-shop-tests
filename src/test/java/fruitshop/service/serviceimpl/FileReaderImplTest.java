package fruitshop.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readDataFromFile_filaNameIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> reader.readDataFromFile(null));
    }

    @Test
    void readDataFromFile_fileNameDoesntExist_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readDataFromFile("hello"));
    }

    @Test
    void readDataFromFile_fileIsEmpty_ok() {
        String fileName = "src/main/resources/empty.csv";
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.readDataFromFile(fileName);
        assertEquals(expected, actual);
    }

    @Test
    void readDataFromFile_dataMatches_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        String fileName = "src/main/resources/fruitdata.csv";
        List<String> actual = reader.readDataFromFile(fileName);
        assertEquals(expected, actual);
    }
}
