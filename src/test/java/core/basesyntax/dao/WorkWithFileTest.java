package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorkWithFileTest {
    private static final String PATH_INPUT = "src/test/resources/input.csv";
    private static final String PATH_OUTPUT = "src/test/resources/output.csv";
    private static final String HEADER = "fruit,quantity";

    private static WorkWithFile file;
    private List<String> tmp;

    @BeforeClass
    public static void creatingWorkWithFileClass() {
        file = new WorkWithFile();
    }

    @Test
    public void checkReadingFromFile_OK() {
        tmp = file.getData(PATH_INPUT);
        assertEquals("type,fruit,quantity", tmp.get(0));
    }

    @Test(expected = RuntimeException.class)
    public void checkReadingFromFile_NotOK() {
        file.getData("src/test/resources");
    }

    @Test
    public void checkWritingInFile_OK() {
        file.putData(PATH_OUTPUT);
        tmp = file.getData(PATH_OUTPUT);
        assertEquals(HEADER, tmp.get(0));
    }

    @Test(expected = RuntimeException.class)
    public void checkWritingInFile_NotOK() {
        file.putData("src/test/resources");
    }

    @After
    public void clearList() {
        if (tmp != null) {
            tmp.clear();
        }
    }
}
