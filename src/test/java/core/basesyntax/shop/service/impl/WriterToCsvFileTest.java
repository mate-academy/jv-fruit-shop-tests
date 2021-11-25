package core.basesyntax.shop.service.impl;

import core.basesyntax.shop.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class WriterToCsvFileTest {
    private static final String OUTPUT_FILE = "src/test/resources//output.csv";

    @AfterClass
    public static void clearAfterTest() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete output file after test");
        }
    }

    @Test
    public void write_writingDataToFile_ok() {
        Writer writer = new WriterToCsvFile();
        String expected = "Stately, plump Buck Mulligan came from the stairhead, "
                + "bearing a bowl of lather on which a mirror and a razor lay crossed. "
                + "A yellow dressinggown, ungirdled, was sustained gently behind him "
                + "by the mild morning air. He held the bowl aloft and intoned:\n"
                + "— Introibo ad altare Dei.";
        writer.write(OUTPUT_FILE, expected);
        String actual = "";
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + OUTPUT_FILE, e);
        }
        Assert.assertEquals(actual, expected);
    }
}
