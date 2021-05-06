package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class ReaderServiceTest {
    private static final ReaderService READER_SERVICE = new ReaderServiceImpl();

    @Test
   public void readFromFile_correctInput_Ok() {
        String file = "src/test/java/resources/text";
        List<String> expected = List.of("operation,fruit,quantity",
                "b,banana,20",
                "b,apple,10",
                "s,banana,10",
                "p,banana,10",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5");
        assertEquals(expected, READER_SERVICE.readFromFile(file));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongFilePath_Not_Ok() {
        String file = "src/main/resources/text1";
        assertEquals(new RuntimeException("Can not read the file..."),
                READER_SERVICE.readFromFile(file));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notFilePath_Not_Ok() {
        String file = "what time is it?";
        assertEquals(new RuntimeException("Can not read the file..."),
                READER_SERVICE.readFromFile(file));
    }
}
