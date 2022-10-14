package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderImplTest {
    public static final String FILE_NAME = "src/main/java/core/basesyntax/resources/data.csv";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFromFile_correctPath_Ok() {
        Path file = Paths.get(FILE_NAME);
        long count = 0;
        List<String> arrayList = fileReader.readFromFile(FILE_NAME);
        try {
            count = Files.lines(file).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(count, arrayList.size());
    }

    @Test
    public void readFromFile_inCorrectPath_NotOk() {
        String fileName = "kkkkk";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't find file by path: " + Path.of(fileName));
        fileReader.readFromFile(fileName);
    }

    @Test
    public void readFromFile_Null() {
        String fileName = null;
        Assert.assertNull(fileName);
    }

    @Test
    public void readFromFile_correctData_Ok() {
        String data = "b,apple,150" + System.lineSeparator()
                + "s,apple,50" + System.lineSeparator()
                + "p,apple,100" + System.lineSeparator()
                + "r,apple,50" + System.lineSeparator()
                + "b,banana,150" + System.lineSeparator()
                + "s,banana,50" + System.lineSeparator()
                + "p,banana,100" + System.lineSeparator()
                + "r,banana,50" + System.lineSeparator()
                + "b,ananas,150" + System.lineSeparator()
                + "s,ananas,50" + System.lineSeparator()
                + "p,ananas,100" + System.lineSeparator()
                + "r,ananas,50" + System.lineSeparator();
        String string = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new java.io.FileReader(FILE_NAME));
            String value = bufferedReader.readLine();
            while (value != null) {
                string += value;
                string += System.lineSeparator();
                value = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(data,string);
    }

    @Test
    public void readFromFile_falseData_Ok() {
        String data = "b,apple,150" + System.lineSeparator()
                + "p,apple,100" + System.lineSeparator()
                + "r,apple,50" + System.lineSeparator()
                + "b,banana,150" + System.lineSeparator()
                + "s,banana,50" + System.lineSeparator()
                + "p,banana,100" + System.lineSeparator()
                + "r,banana,50" + System.lineSeparator()
                + "b,ananas,150" + System.lineSeparator()
                + "s,ananas,50" + System.lineSeparator()
                + "p,ananas,100" + System.lineSeparator()
                + "r,ananas,50" + System.lineSeparator();
        String string = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new java.io.FileReader(FILE_NAME));
            String value = bufferedReader.readLine();
            while (value != null) {
                string += value;
                string += System.lineSeparator();
                value = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(data,string);
    }
}
