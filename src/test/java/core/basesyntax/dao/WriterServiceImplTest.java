package core.basesyntax.dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private ReaderService readerService;
    private WriterService writerService;
    private String toFile = "src/main/java/resources/outputFile.csv";
    private List<String> result;
    private boolean thrown = false;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void writeData_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana 24");
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity");
        stringBuilder.append("\n")
                .append("fruit,quantity")
                .append("\n")
                .append("banana 24");
        try {
            writerService.writeData(toFile,stringBuilder.toString());
        } catch (RuntimeException e) {
            thrown = true;
        }
        result = readerService.getDataFromFile(toFile);
        Assert.assertFalse(thrown);
        Assert.assertEquals(expected,result);
    }
}
