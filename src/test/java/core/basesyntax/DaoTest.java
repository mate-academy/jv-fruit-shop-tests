package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FileDaoCsv;
import core.basesyntax.dao.FileDaoCsvImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DaoTest {
    private static ArrayList<String> correctInput;
    private static final String INVALID_DATA_FILE = "file_incorrect_1_name";
    private static final String VALID_DATA_FILE = "report.csv";
    private FileDaoCsv fileDaoCsv = new FileDaoCsvImpl();

    @BeforeAll
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
    void wrongFileName() {
        assertThrows(RuntimeException.class, () -> fileDaoCsv.getData(INVALID_DATA_FILE));
    }

    @Test
    void correctDataFromFile() {
        List<String> actual = fileDaoCsv.getData(VALID_DATA_FILE);
        assertEquals(correctInput, actual, "Reading from file result is incorrect");
    }
}
