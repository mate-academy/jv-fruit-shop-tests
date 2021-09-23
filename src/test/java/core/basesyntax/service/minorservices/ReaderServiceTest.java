package core.basesyntax.service.minorservices;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ReaderServiceTest {
    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();
    private static File validFile;
    private static File emptyFile;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        try {
            validFile = folder.newFile("validFile.txt");
            emptyFile = folder.newFile("emptyFile.txt");
        } catch (IOException e) {
            throw new RuntimeException("Can't create temporary test file in "
                    + ReaderServiceTest.class, e);
        }
    }

    @Test
    public void readFromFile_validValue_Ok() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(validFile))) {
            bufferedWriter.write("type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "s,apple,100");
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + validFile.getName(), e);
        }
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("s,apple,100");
        List<String> actual = readerService.readFromFile(validFile.getPath());
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_validValueEmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(emptyFile.getPath());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidValue_notOk() {
        readerService.readFromFile("Invalid file path");
    }
}
