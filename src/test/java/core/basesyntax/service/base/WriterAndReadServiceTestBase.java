package core.basesyntax.service.base;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public abstract class WriterAndReadServiceTestBase<W extends WriterService,
        R extends ReaderService> {
    private W writerInstance;
    private R readerInstance;

    protected abstract W createWriterInstance();

    protected abstract R createReaderInstance();

    @Before
    public void setUp() {
        writerInstance = createWriterInstance();
        readerInstance = createReaderInstance();
    }

    @Test
    public void read_Data_From_Correct_File_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        assertEquals(expected, readerInstance.readFromFile("src/test/resources/read-test.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void read_Data_From_Incorrect_File_notOk() {
        readerInstance.readFromFile("src/test/resources/read-test1.csv");
    }

    @Test
    public void write_Data_To_Correct_File_ok() {
        String testString = "test"
                + System.lineSeparator()
                + "writing"
                + System.lineSeparator()
                + "successful";
        List<String> expected = List.of(
                "test",
                "writing",
                "successful"
        );
        writerInstance.writeToFile(testString, "src/test/resources/write-test.csv");
        assertEquals(expected, readerInstance.readFromFile("src/test/resources/write-test.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void write_Data_To_Incorrect_File_notOk() {
        writerInstance.writeToFile("test", "src/test/resources/write-test1.csv");
    }
}
