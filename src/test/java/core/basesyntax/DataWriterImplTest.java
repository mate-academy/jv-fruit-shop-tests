package core.basesyntax;

import core.basesyntax.service.DataWriter;
import core.basesyntax.service.impl.DataWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataWriterImplTest {
    private static final String APPLES_GRAPES_REPORT = "applesGrapesReport.csv";
    private DataWriter dataWriter;

    @Before
    public void setUp() {
        dataWriter = new DataWriterImpl();
    }

    @Test
    public void writeData_Ok() {
        String expectedResult = "fruit,quantity\nbanana,185\norange,75";
        dataWriter.writeData(APPLES_GRAPES_REPORT, expectedResult);
        List<String> strings;
        try {
            strings = Files.readAllLines(Path.of(APPLES_GRAPES_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file during tests. File: "
                    + APPLES_GRAPES_REPORT, e);
        }
        String actualResult = strings.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataOfNull_NotOk() {
        dataWriter.writeData(APPLES_GRAPES_REPORT, null);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToWrongFileName_NotOk() {
        dataWriter.writeData("",
                "Some data, which we have to write");
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToNullFile_NotOk() {
        dataWriter.writeData(null,
                "Some data, which we have to write");
    }
}
