package core.basesyntax;

import fm.FileManager;
import fm.FileManagerCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class FileManagerCsvImplTest {
    private static final FileManager fileManager = new FileManagerCsvImpl();
    private static final String FILE_NAME = "fruit-shop-report.csv";

    @Test
    public void writeToFile_Ok() {
        String expected = "type,fruit,quantity\n"
                + "b,grape,50\n"
                + "b,melon,30";
        fileManager.writeToFile(FILE_NAME, expected);
        String actual = getDataFromFile();
        Assert.assertEquals(expected, actual);

        expected = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10";
        fileManager.writeToFile(FILE_NAME, expected);
        actual = getDataFromFile();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_NotOk() {
        String expected = "type,fruit,quantity\n"
                + "b,grape,50\n"
                + "b,melon,30";
        // Can't import jupiter because of JUnit4
        // So can't use AssertThrows also
        try {
            fileManager.writeToFile("", expected);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Runtime exception should be thrown because of invalid filename!");
    }

    private String getDataFromFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.readAllLines(Path.of(FILE_NAME))
                    .forEach(s -> stringBuilder.append(s).append("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + FILE_NAME);
        }
        return stringBuilder.toString().trim();
    }
}
