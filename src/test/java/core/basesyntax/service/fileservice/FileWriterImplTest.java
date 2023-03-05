package core.basesyntax.service.fileservice;

import static junit.framework.TestCase.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterImplTest {
    private static final String EMPTY_NAME = "";
    private static final String RECORDED_DATA = "type,fruit,quantity";
    private static final String FILE_NAME = "fileName.csv";
    private static final String OTHER_FORMAT = "txtFileFormat.txt";
    private static List<String> dataFromFile;
    private static FileWriter writer;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new FileWriterImpl();
        dataFromFile = new ArrayList<>();
    }

    @Test
    public void write_emptyFileName_notOk() {
        thrown.expect(RuntimeException.class);
        writer.write(EMPTY_NAME, RECORDED_DATA);
    }

    @Test
    public void write_nullFileName_notOk() {
        thrown.expect(RuntimeException.class);
        writer.write(null, RECORDED_DATA);
    }

    @Test
    public void write_nullToFile_notOk() {
        thrown.expect(RuntimeException.class);
        writer.write(FILE_NAME, null);
    }

    @Test
    public void write_otherFormatFileName_notOk() {
        thrown.expect(RuntimeException.class);
        writer.write(OTHER_FORMAT, RECORDED_DATA);
    }

    @Test
    public void write_Ok() {
        writer.write(FILE_NAME, RECORDED_DATA);
        try {
            dataFromFile = Files.readAllLines(new File(FILE_NAME).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file " + FILE_NAME, e);
        }
        assertEquals(RECORDED_DATA, dataFromFile.get(0));
    }

    @AfterClass
    public static void afterClass() {
        File file = new File(FILE_NAME);
        file.delete();
    }
}
