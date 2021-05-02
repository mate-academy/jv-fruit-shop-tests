package core.basesyntax.fruitshop.file.reader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final FileReaderImpl FILE_READER = new FileReaderImpl();

    @Test
    public void read_customInput_ok() {
        String fileName = "src/test/TestInput.csv";
        List<String> expected = List.of("apple", "banana", "apricot");
        List<String> actual = FILE_READER.read(fileName);
        assertEquals(expected, actual);
    }

    @Test
    public void read_actualInput_ok() {
        String fileName = "src/test/ActualInput.csv";
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple ,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple ,10",
                "p,apple ,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = FILE_READER.read(fileName);
        assertEquals(expected, actual);
    }

    @Test
    public void read_emptyEntry_ok() {
        String fileName = "src/test/EmptyInput.csv";
        List<String> expected = new ArrayList<>();
        List<String> actual = FILE_READER.read(fileName);
        assertEquals(expected, actual);
    }
}
