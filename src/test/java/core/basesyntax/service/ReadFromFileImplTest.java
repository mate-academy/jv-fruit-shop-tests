package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReadFromFileImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileImplTest {
    private static final String NOT_EXIST_PATH = "src/main/resources/invalidPath.csv";
    private static final String EXIST_PATH = "src/test/java/core/"
            + "basesyntax/resourses/inputFile.csv";
    private static final List<String> expected = List.of(
            "type,fruit,quantity",
            "b,banana,20", "b,apple,100",
            "s,banana,100", "p,banana,13",
            "r,apple,10", "p,apple,20",
            "p,banana,5", "s,banana,50");
    private static ReadFromFile reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReadFromFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromNotExistPath_notOk() {
        reader.readFile(NOT_EXIST_PATH);
    }

    @Test
    public void readFromExistPath_ok() {
        List<String> actual = reader.readFile(EXIST_PATH);
        assertEquals(expected, actual);
    }
}
