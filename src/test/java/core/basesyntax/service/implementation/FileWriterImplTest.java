package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import java.io.File;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private static final String FILE_NAME = "src/test/resources/Output.csv";

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void write_data_isOk() {
        fileWriter.write("fruit,quantity\napple,100", FILE_NAME);
        File file = new File(FILE_NAME);
        assertTrue(file.exists());
        List<String> actual = fileReader.read(FILE_NAME);
        List<String> expected = List.of("fruit,quantity", "apple,100");
        assertEquals(expected, actual);
    }
}
