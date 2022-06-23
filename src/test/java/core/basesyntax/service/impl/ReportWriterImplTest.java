package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static ReportWriter reportWriter;
    private static final String PATH_NAME = "src/test/java/core/basesyntax/report/testReport.csv";

    @BeforeAll
    static void beforeAll() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    void reportWriter_Ok() {
        String expected = "fruit, quantity";
        reportWriter.reportWriter(PATH_NAME, expected);
        try {
            String actual = Files.readString(Path.of(PATH_NAME));
            Assert.assertEquals(actual, expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from testFile", e);
        }

    }
}
