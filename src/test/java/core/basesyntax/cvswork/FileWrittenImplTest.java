package core.basesyntax.cvswork;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWrittenImplTest {
    private static FileWriter writer;

    @BeforeAll
    static void beforeAll() {
        writer = new FileWrittenImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void write_emptyFile_ok() {
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

    @Test
    public void write_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> writer.write("FileFailed.cvs"));
    }
}
