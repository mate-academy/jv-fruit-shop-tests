package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FileServiceImplTest {
    private final FileService fileService = new FileServiceImpl();

    @Test(expected = RuntimeException.class)
    public void readFileByWrongFileName() {
        String fileName = "anotherFileName";
        fileService.readFile(fileName);
    }

    @Test
    public void readFileByCorrectName_Ok() {
        String fileName = "input.txt";
        List<String> excepted = Arrays.asList("1","2","3","4","5");
        List<String> actual = fileService.readFile(fileName);
        assertEquals(excepted,actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFileByWrongFileName() {
        String content = "some content";
        String wrongFilename = "";
        fileService.writeFile(wrongFilename,content);
    }

    @Test
    public void writeFileByCorrectName_Ok() {
        String fileName = "output.txt";
        String outputFileNewContent = "5\n4\n3\n2\n1";
        List<String> excepted = Arrays.asList("5","4","3","2","1");
        fileService.writeFile(fileName, outputFileNewContent);
        List<String> actual = fileService.readFile(fileName);
        assertEquals(excepted,actual);
    }
}
