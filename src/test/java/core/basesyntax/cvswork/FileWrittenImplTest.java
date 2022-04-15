package core.basesyntax.cvswork;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class FileWrittenImplTest {
    private static FileWriter writer;

    @BeforeAll
    public static void init() {
        writer = new FileWrittenImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void is_valid_ok() {
        init();
        Fruit guavaFruit = new Fruit(110, "guava");
        Fruit bananaFruit = new Fruit(440, "banana");
        Fruit appleFruit = new Fruit(170, "apple");
        Fruit strawberryFruit = new Fruit(190, "strawberry");
        Storage.fruits.add(guavaFruit);
        Storage.fruits.add(bananaFruit);
        Storage.fruits.add(appleFruit);
        Storage.fruits.add(strawberryFruit);
        writer.write("src/test/java/core/basesyntax/resourse/FileWritten.cvs");
    }

    @Test(expected = RuntimeException.class)
    public void is_incorrect_Path_NotOk() {
        writer.write("FileFailed.cvs");
    }

    @Test(expected = RuntimeException.class)
    public void is_EmptyList_ok() {
        writer.write("FileFailed.cvs");
    }
}
