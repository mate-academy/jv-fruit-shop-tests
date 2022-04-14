package core.basesyntax.cvswork;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ManipulationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private List<Fruit> fruits = new ArrayList();
    private ManipulationService manipulation;
    private List<String> fruitTransactions = new ArrayList();
    private List<String> emptyFile = new ArrayList();
    private List<String> transactions = new ArrayList();

    @Before
    public void setUp() {
        FileReader read = new FileReaderImpl();
        transactions = read.read("src/test/java/core/basesyntax/resourse/withoutName.cvs");
        emptyFile = read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs");
        fruitTransactions = read.read("src/test/java/core/basesyntax/resourse/normalFile.cvs");

    }

    @Test
    public void is_valid_null_List() {
        int expected = 0;
        int actual = emptyFile.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void is_valid() {
        int expected = 15;
        int actual = fruitTransactions.size();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void is_incorrect_Path() {
        transactions = new FileReaderImpl().read("Incorrect.cvsPath");
    }
}
