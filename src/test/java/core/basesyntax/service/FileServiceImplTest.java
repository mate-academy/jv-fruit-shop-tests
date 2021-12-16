package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FileServiceImplTest {
    private final FileService fileService = new FileServiceImpl();

    @Test(expected = RuntimeException.class)
    public void readFileWrong() {
        String fileName = "Hello";
        fileService.readFile(fileName);
    }

    @Test
    public void readFileCorrect_Ok() {
        String fileName = "src/test/resources/input.txt";
        List<String> expected = Arrays.asList("100", "200", "300");
        List<String> actual = fileService.readFile(fileName);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_NotOk() {
        String text = "Hi Hi Hi";
        String wrongName = "";
        fileService.writeFile(wrongName, text);
    }

    @Test
    public void writeFile_Ok() {
        String fileName = "src/test/resources/output.txt";
        String outputFile = "5\n4\n3\n2\n1";
        List<String> expected = Arrays.asList("5", "4", "3", "2", "1");
        fileService.writeFile(fileName, outputFile);
        List<String> actual = fileService.readFile(fileName);
        assertEquals(expected, actual);
    }
}
