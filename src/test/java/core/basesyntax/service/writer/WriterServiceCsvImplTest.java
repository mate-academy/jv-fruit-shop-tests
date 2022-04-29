package core.basesyntax.service.writer;

import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class WriterServiceCsvImplTest {
    private WriterService writer = new WriterServiceCsvImpl();

    @Test
    public void writer_createFileWithEmptyFilePass_notOk() {
        List<FruitTransaction> fruits = new ArrayList<>();
        String outputFile = "";
        try {
            writer.writeToFile(outputFile, fruits);
        } catch (RuntimeException e) {
            return;
        }
        fail("Test should be fail -> expected RuntimeException");
    }
}
