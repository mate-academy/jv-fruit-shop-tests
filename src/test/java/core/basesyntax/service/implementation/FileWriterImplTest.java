package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String FILE_NAME = "src/test/resources/Output.csv";

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_data_isOk() throws IOException {
        fileWriter.write("fruit,quantity\napple,100", FILE_NAME);
        File file = new File(FILE_NAME);
        assertTrue(file.exists());

        List<String> actual = Files.readAllLines(Path.of(FILE_NAME));
        List<String> expected = List.of("fruit,quantity", "apple,100");
        assertEquals(expected, actual);
    }
}
