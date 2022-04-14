package core.basesyntax.cvswork;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWrittenImplTest {
    private List<Fruit> fruitsEmpty = new ArrayList();
    private Fruit fruit;
    private Fruit fruitOne;
    private Fruit fruitTwo;
    private Fruit fruitThree;
    private FileWriter writer;

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit(110, "guava");
        fruitOne = new Fruit(440, "banana");
        fruitTwo = new Fruit(170, "apple");
        fruitThree = new Fruit(190, "strawberry");
    }

    @Test
    public void is_valid_null_List() {
        int expected = 0;
        int actual = fruitsEmpty.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void is_valid() {
        Storage.fruits.add(fruit);
        Storage.fruits.add(fruitOne);
        Storage.fruits.add(fruitTwo);
        Storage.fruits.add(fruitThree);
        writer = new FileWrittenImpl();
        writer.write("src/test/java/core/basesyntax/resourse/FileWritten.cvs");
    }

    @Test(expected = RuntimeException.class)
    public void is_incorrect_Path() {
        writer = new FileWrittenImpl();
        writer.write("FileFailed.cvs");
    }
}
