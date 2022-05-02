package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReader fileReader;
    private static String inputPath;
    private static String emptyInputPath;

    @BeforeClass
    public static void setUpFirst() {
        fileReader = new FileReaderImpl();
        inputPath = "src/test/testResources/input.csv";
        emptyInputPath = "src/test/testResources/emptyInput.csv";
    }

    @Test
    public void read_correctFileName_Ok() {
        assertEquals(List.of("test"), fileReader.read(inputPath));
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongFileName_NotOk() {
        fileReader.read("wrong name");
    }

    @Test(expected = NullPointerException.class)
    public void read_nullValue_NotOk() {
        fileReader.read(null);
    }

    @Test
    public void read_emptyFile_Ok() {
        List<String> actual = fileReader.read(emptyInputPath);
        assertEquals(List.of(), actual);
    }
}
