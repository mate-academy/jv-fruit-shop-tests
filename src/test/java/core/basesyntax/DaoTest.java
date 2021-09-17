package core.basesyntax;

import core.basesyntax.dao.FileDaoCsv;
import core.basesyntax.dao.FileDaoCsvImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DaoTest {
    private static ArrayList<String> correctInput;
    private static final String INVALID_DATA_FILE = "file_incorrect_1_name";
    private static final String VALID_DATA_FILE = "report.csv";
    private FileDaoCsv fileDaoCsv = new FileDaoCsvImpl();

    @BeforeClass
    public static void beforeAll() {
        correctInput = new ArrayList<>();
        correctInput.add("type,fruit,quantity");
        correctInput.add("b,banana,150");
        correctInput.add("b,apple,100");
        correctInput.add("s,banana,100");
        correctInput.add("p,banana,13");
        correctInput.add("r,apple,10");
        correctInput.add("p,apple,20");
        correctInput.add("p,banana,5");
        correctInput.add("s,banana,100");
    }

    @Test
    public void wrongFileName_NotOK() {
        Assert.assertThrows(RuntimeException.class, () -> fileDaoCsv.getData(INVALID_DATA_FILE));
    }

    @Test
    public void correctDataFromFile_OK() {
        List<String> actual = fileDaoCsv.getData(VALID_DATA_FILE);
        Assert.assertEquals("Reading from file result is incorrect", correctInput, actual);
    }
}
