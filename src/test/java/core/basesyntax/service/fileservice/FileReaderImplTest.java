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

public class FileReaderImplTest {
    private static final String EMPTY_FILE = "emptyFile.csv";
    private static final String EMPTY_NAME = "";
    private static final String OTHER_FORMAT = "txtFileFormat.txt";
    private static final String MISSING_FILE = "missingFile.csv";
    private static final String VALID_NAME = "validFile.csv";
    private static List<String> validOutputData;
    private static FileReader reader;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new FileReaderImpl();
        validOutputData = new ArrayList<>();
    }

    @Test
    public void read_emptyFilename_notOk() {
        thrown.expect(RuntimeException.class);
        reader.read(EMPTY_NAME);
    }

    @Test
    public void read_nullFilename_notOk() {
        thrown.expect(RuntimeException.class);
        reader.read(null);
    }

    @Test
    public void read_emptyFile_notOk() {
        try {
            Files.write(new File(EMPTY_FILE).toPath(), EMPTY_NAME.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file" + EMPTY_FILE);
        }
        thrown.expect(RuntimeException.class);
        reader.read(EMPTY_FILE);
    }

    @Test
    public void read_otherFileFormat_notOk() {
        thrown.expect(RuntimeException.class);
        reader.read(OTHER_FORMAT);
    }

    @Test
    public void read_missingFile_notOk() {
        thrown.expect(RuntimeException.class);
        reader.read(MISSING_FILE);
    }

    @Test
    public void read_file_Ok() {
        validOutputData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        String validFileContent = String.join("", "type,fruit,quantity\n",
                "b,banana,20\n",
                "b,apple,100\n",
                "s,banana,100\n",
                "p,banana,13\n");
        try {
            Files.write(new File(VALID_NAME).toPath(), validFileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file" + VALID_NAME);
        }
        assertEquals(validOutputData, reader.read(VALID_NAME));
    }

    @AfterClass
    public static void afterClass() {
        File emptyFile = new File(EMPTY_FILE);
        emptyFile.delete();
        File validFile = new File(VALID_NAME);
        validFile.delete();
    }
}
