package core.basesyntax.service;

import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderImplTest {
    public static final String FILE_NAME = "src/test/java/core/basesyntax/resources/data.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private FileReader fileReader;

    @Before
    public void beforEachTest() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_correctPath_ok() {
        List<String> expected = List.of("b,apple,150","s,apple,50","p,apple,100","r,apple,50",
                "b,banana,150","s,banana,50","p,banana,100","r,banana,50",
                "b,ananas,150","s,ananas,50","p,ananas,100","r,ananas,50");
        List<String> actual = fileReader.readFromFile(FILE_NAME);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_inCorrectPath_notOk() {
        String fileName = "kkkkk";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't find file by path: " + Path.of(fileName));
        fileReader.readFromFile(fileName);
    }

    @Test
    public void readFromFile_null_notOk() {
        String fileName = null;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't find file by path: " + Path.of(fileName));
        fileReader.readFromFile(fileName);
    }
}
