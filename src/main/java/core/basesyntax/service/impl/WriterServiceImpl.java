package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class WriterServiceImpl implements WriterService {
    private static final String FILE_FOR_WRITE = "src/main/resources/reportTotalResult.csv";

    @Override
    public void writeFile(List<Transaction> list, String fileOutput) {
        File csvOutputFile = new File(fileOutput);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("fruit,quantity");
            for (Transaction result : list) {
                pw.println(result.getFruit() + "," + result.getQuantity());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't create file " + FILE_FOR_WRITE);
        }
    }
}
