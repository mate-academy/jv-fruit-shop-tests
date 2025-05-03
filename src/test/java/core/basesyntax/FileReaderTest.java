package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_File_isOk() {
        File inputDataFile = new File("src/test/resources/isOkReadFile.csv");
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        List<String> actual = fileReader.readFile(inputDataFile);
        assertEquals(expected, actual);
    }

    @Test
    void read_File_NotOk() {
        File inputDataFile = new File("");
        assertThrows(RuntimeException.class, () -> fileReader.readFile(inputDataFile));
    }
}
