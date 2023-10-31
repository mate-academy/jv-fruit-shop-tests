package fruitshop.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.service.FileReader;
import fruitshop.service.FileWriter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void writeDataToTheFile_filaNameIsNull_notOk() {
        String fileName = null;
        assertThrows(NullPointerException.class,
                () -> writer.writeDataToTheFile(fileName, "itDoesn'tMatter"));
    }

    @Test
    void writeDataToTheFile_dataMatches_ok() {
        FileReader reader = new FileReaderImpl();
        String expected = "type,fruit,quantity" + System.lineSeparator()
                 + "b,banana,20" + System.lineSeparator()
                 + "b,apple,100" + System.lineSeparator()
                 + "s,banana,100" + System.lineSeparator()
                 + "p,banana,13" + System.lineSeparator()
                 + "r,apple,10" + System.lineSeparator()
                 + "p,apple,20" + System.lineSeparator()
                 + "p,banana,5" + System.lineSeparator()
                 + "s,banana,50";
        String fileName = "src/main/resources/test.csv";
        writer.writeDataToTheFile(fileName, expected);
        List<String> result = reader.readDataFromFile(fileName);
        String actual = String.join(System.lineSeparator(), result);
        assertEquals(expected, actual);
    }
}
