package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainTest {
    @Test
    public void main_total_ok() {
        String from = "src/main/resources/test.csv";
        String to = "src/main/resources/out.csv";
        Main.main(from,
                to);
        try {
            Assert.assertEquals(Files.readString(Path.of(to)),
                    "fruit, quantity" + System.lineSeparator() +
                            "banana, 5" + System.lineSeparator() +
                            "apple, 25");

        } catch (IOException e) {
            throw new RuntimeException("Can't read the output file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void reader_read_nonExistentFile_exception() {
        String from = "";
        new ReaderServiceImpl().read(from);
    }

    @Test
    public void reader_read_ok() {
        String from = "src/main/resources/test.csv";
        Assert.assertEquals(new ReaderServiceImpl().read(from),
                List.of("action,fruit,amount",
                        "b,banana,10",
                        "b,apple,20",
                        "p,banana,5",
                        "s,apple,5")
        );
    }

    @Test
    public void transactionParser_parse_ok() {
        List<String> read = new ArrayList<>();
        read.add("action,fruit,amount");
        read.add("b,banana,10");
        read.add("b,apple,20");
        read.add("p,banana,5");
        read.add("s,apple,5");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("b"),
                "banana",
                10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("b"),
                "apple",
                20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("p"),
                "banana",
                5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("s"),
                "apple",
                5));
        List<FruitTransaction> actual = new TransactionParserServiceImpl().parse(read);
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void transactionParser_parse_emptyInput_exception() {
        List<String> read = new ArrayList<>();
        new TransactionParserServiceImpl().parse(read);
    }

    @Test
    public void reporterMaker_report_ok() {
        Map<String, Integer> base = Map.of(
                        "banana",
                        5,
                        "apple",
                        25
        );
        String expected = "fruit, quantity" + System.lineSeparator() +
                "banana, 5" + System.lineSeparator() +
                "apple, 25";
        String actual = new ReportMakerServiceImpl().report(base);
        boolean result = expected.equals(actual);
        Assert.assertTrue(result);
    }

    @Test
    public void writer_write_ok() {
        String toWrite = "fruit, quantity" + System.lineSeparator() +
                "banana, 5" + System.lineSeparator() +
                "apple, 25";
        String to = "src/main/resources/out.csv";
        new WriterServiceImpl().write(toWrite, to);
        try {
            Assert.assertEquals(toWrite, Files.readString(Path.of(to)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the output file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void writer_write_nonExistentFile_exception() {
        new WriterServiceImpl().write("Writing something in the file",
                "");
    }
}
