package core.basesyntax.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RecordDaoImplTest {
    private static RecordDao recordDao;

    @BeforeClass
    public static void beforeClass() {
        recordDao = new RecordDaoImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFile_Not_Ok() {
        String fileNamePath = "badFileName";
        recordDao.readFile(fileNamePath);
    }

    @Test()
    public void readFile_Ok() {
        String fileNamePath = "src/test/resources/input-test.txt";
        List<String> expected = Arrays.asList("Hello","World","Mates","!");
        List<String> actual = recordDao.readFile(fileNamePath);
        recordDao.readFile(fileNamePath);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_Not_Ok() {
        String content = "Ahahhaa, content";
        String fileNamePath = "";
        recordDao.writeFile(fileNamePath, content);
    }

    @Test
    public void writeFileByCorrectName_Ok() {
        String fileName = "output-test.txt";
        String outputFileNewContent = "Hello\nWorld\nMates\n!";
        List<String> excepted = Arrays.asList("Hello","World","Mates","!");
        recordDao.writeFile(fileName, outputFileNewContent);
        List<String> actual = recordDao.readFile(fileName);
        Assert.assertEquals(excepted,actual);
    }
}