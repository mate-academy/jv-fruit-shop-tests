package core.basesyntax.cvswork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static FileReader read;

    @BeforeAll
    public static void beforeAll() {
        read = new FileReaderImpl();
    }

    @Test
    public void read_emptyFile_ok() {
        int expected = 0;
        List<String> emptyFile =
                read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs");
        int actual = emptyFile.size();
        assertEquals(expected, actual);
    }

    @Test
    public void read_notEmptyFile_ok() {
        List<String> fruitTransactions =
                read.read("src/test/java/core/basesyntax/resourse/normalFile.cvs");
        int expected = 15;
        int actual = fruitTransactions.size();
        assertEquals(expected, actual);
    }

    @Test
    public void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> read.read("Incorrect.cvsPath"));
    }
}
