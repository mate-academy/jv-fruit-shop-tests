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
    private static final String VALID_FILE = "validFile.csv";
    private static List<String> validOutputData;
    private static FileReader reader;

    private static File emptyFile;
    private static File validFile;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new FileReaderImpl();
        validOutputData = new ArrayList<>();
        emptyFile = new File(EMPTY_FILE);
        try {
            Files.write(emptyFile.toPath(), EMPTY_NAME.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file" + EMPTY_FILE);
        }
        validFile = new File(VALID_FILE);
        String validFileContent = String.join("", "type,fruit,quantity\n",
                "b,banana,20\n",
                "b,apple,100\n",
                "s,banana,100\n",
                "p,banana,13\n");
        try {
            Files.write(validFile.toPath(), validFileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file" + VALID_FILE);
        }
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
        assertEquals(validOutputData, reader.read(VALID_FILE));
    }

    @AfterClass
    public static void afterClass() {
        emptyFile.delete();
        validFile.delete();
    }
}
