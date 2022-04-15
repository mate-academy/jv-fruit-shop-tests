package core.basesyntax.cvswork;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class FileReaderImplTest {
    private static FileReader read;

    @BeforeAll
    public static void init() {
        read = new FileReaderImpl();
    }

    @Test
    public void is_valid_null_List_ok() {
        init();
        int expected = 0;
        List<String> emptyFile =
                read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs");
        int actual = emptyFile.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void is_valid_ok() {
        List<String> fruitTransactions =
                read.read("src/test/java/core/basesyntax/resourse/normalFile.cvs");
        int expected = 15;
        int actual = fruitTransactions.size();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void is_incorrect_Path_NotOk() {
        List<String> transactions = read.read("Incorrect.cvsPath");
    }
}
