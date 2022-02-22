package core.basesyntax.service.fileservice;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    public static final String DATA_TO_WRITE = "Data to write";
    public static final String FILE_NAME = "file.csv";
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new WriterImpl();
    }

    @Test
    public void write_emptyFileName_notOk() {
        String emptyFileName = "";
        assertThrows(RuntimeException.class, () -> writer.write(emptyFileName, DATA_TO_WRITE));
    }

    @Test
    public void write_nullFileName_notOk() {
        assertThrows(RuntimeException.class, () -> writer.write(null, DATA_TO_WRITE));
    }

    @Test
    public void write_nullToFile_notOk() {
        assertThrows(RuntimeException.class, () -> writer.write(FILE_NAME, null));
    }

    @Test
    public void write_wrongFormatFileName_notOk() {
        String wrongFormatFileName = "file.txt";
        assertThrows(RuntimeException.class,
                () -> writer.write(wrongFormatFileName, DATA_TO_WRITE));
    }

    @Test
    public void write_Ok() {
        writer.write(FILE_NAME, DATA_TO_WRITE);
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(new File(FILE_NAME).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cann`t read file " + FILE_NAME, e);
        }
        assertEquals(DATA_TO_WRITE, dataFromFile.get(0));
    }

    @AfterClass
    public static void afterClass() {
        File file = new File(FILE_NAME);
        file.delete();
    }
}
