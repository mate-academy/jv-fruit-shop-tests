package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_OUTPUT = "src/test/resources/output.csv";
    private static final String ETALON_STRING = "fruit,quantity";
    private static FileWriterImpl fileWriter;

    @BeforeClass
    public static void init() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_correctFile_ok() throws IOException {
        fileWriter.write(PATH_OUTPUT, ETALON_STRING);
        String string = Files.readAllLines(Path.of(PATH_OUTPUT)).get(0);
        assertEquals(string, ETALON_STRING);
    }
}
