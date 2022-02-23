package core.basesyntax.service;

import core.basesyntax.model.FruitDto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadServiceTest extends Assert {
    private static ReadService readService;
    private final File file = new File("src/test/resources/Input_test1.csv");
    private final List<FruitDto> correctReadData = List.of(new FruitDto("banana",
            "b", Integer.parseInt("200")),
            new FruitDto("apple", "b", Integer.parseInt("100")),
            new FruitDto("banana", "s", Integer.parseInt("50")),
            new FruitDto("banana", "p", Integer.parseInt("133")),
            new FruitDto("apple", "r", Integer.parseInt("104")),
            new FruitDto("apple", "p", Integer.parseInt("29")),
            new FruitDto("banana", "p", Integer.parseInt("51")),
            new FruitDto("banana", "s", Integer.parseInt("50")));

    @BeforeClass
    public static void beforeClass() {
        readService = new ReadService();
    }

    @Test
    public void readData_file_ok() {
        final String text = "b,banana,200" + System.lineSeparator() + "b,apple,100"
                + System.lineSeparator() + "s,banana,50" + System.lineSeparator() + "p,banana,133"
                + System.lineSeparator() + "r,apple,104" + System.lineSeparator() + "p,apple,29"
                + System.lineSeparator() + "p,banana,51" + System.lineSeparator() + "s,banana,50";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException("Error!", e);
        }
        final List<FruitDto> list = readService.readData(String.valueOf(file));
        final int actual = list.size();
        final int excepted = correctReadData.size();
        assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_emptyFile_notOk() {
        final String emptyText = "";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(emptyText);
        } catch (IOException e) {
            throw new RuntimeException("Error!", e);
        }
        final List<FruitDto> data = readService.readData(String.valueOf(file));
        final int excepted = 0;
        final int actual = data.size();
        assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_invalidContent_notOk() {
        final String corruptedText = "b,banana,200" + System.lineSeparator() + "b,apple,100"
                + System.lineSeparator() + ",banana,50" + System.lineSeparator() + "p,banana,133"
                + System.lineSeparator() + "r,apple," + System.lineSeparator() + "p,apple,29"
                + System.lineSeparator() + "p,banana,51" + System.lineSeparator() + "s,50";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(corruptedText);
        } catch (IOException e) {
            throw new RuntimeException("Error!", e);
        }
        final List<FruitDto> data = readService.readData(String.valueOf(file));
        final int excepted = 6;
        final int actual = data.size();
        assertEquals(excepted, actual);
    }

    @After
    public void cleanData() {
        try {
            FileWriter fstreamTwo = new FileWriter(file);
            BufferedWriter out1 = new BufferedWriter(fstreamTwo);
            out1.write("");
            out1.close();
        } catch (IOException e) {
            throw new RuntimeException("Delete text", e);
        }
    }
}
