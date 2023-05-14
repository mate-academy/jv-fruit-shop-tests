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
    private static final String TEST_FILE = "testFile.csv";
    private static final String OTHER_FORMAT = "txtFileFormat.txt";
    private static List<String> dataFromFile;
    private static FileWriter writer;
    private static File testFile;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new FileWriterImpl();
        dataFromFile = new ArrayList<>();
        testFile = new File(TEST_FILE);
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
        writer.write(TEST_FILE, null);
    }

    @Test
    public void write_otherFormatFileName_notOk() {
        thrown.expect(RuntimeException.class);
        writer.write(OTHER_FORMAT, RECORDED_DATA);
    }

    @Test
    public void write_Ok() {
        writer.write(TEST_FILE, RECORDED_DATA);
        try {
            dataFromFile = Files.readAllLines(testFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file " + TEST_FILE, e);
        }
        assertEquals(RECORDED_DATA, dataFromFile.get(0));
    }

    @AfterClass
    public static void afterClass() {
        testFile.delete();
    }
}
