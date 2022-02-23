package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.CsvReaderImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReaderService fileReader;
    private static File testFileOk;
    private static File testFileEmpty;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new CsvReaderImpl();
        testFileOk = new File("./src/resources/test_Ok.csv");
        testFileEmpty = new File("./src/resources/test_empty.csv");
        String testFileContent =
                "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50";
        try {
            testFileEmpty.createNewFile();
            Files.write(testFileOk.toPath(), testFileContent.getBytes(), StandardOpenOption.CREATE);

        } catch (IOException e) {
            throw new RuntimeException("FAILED TO CREATE AND WRITE: cant write line: "
                    + testFileContent + ", to file: "
                    + System.lineSeparator() + testFileOk.getName());
        }
    }

    @Test
    public void read_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.read(testFileOk.toString());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_NullParameter_notOk() {
        fileReader.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileDoesNotExist_notOk() {
        fileReader.read("non existent file");
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyFile_notOk() {
        fileReader.read(testFileEmpty.toString());
    }

    @AfterClass
    public static void afterClass() {
        testFileOk.delete();
        testFileEmpty.delete();
    }
}
