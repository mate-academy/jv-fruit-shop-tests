package core.basesyntax.serviceimpl;

import core.basesyntax.service.WriteService;
import core.basesyntax.service.impl.WriteServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String VALID_PATH = "src/test/java/resources/testreport.csv.csv";
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private final WriteService writeService = new WriteServiceImpl();

    @Test(expected = RuntimeException.class)
    public void write_validPath_ok() {
        writeService.writeToFile(report, VALID_PATH);
        Assert.assertTrue(Files.exists(Path.of(VALID_PATH)));
    }

    @Test (expected = RuntimeException.class)
    public void write_nullValue_notOk() {
        writeService.writeToFile(null, VALID_PATH);
    }
}
