package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterImpl implements Writer {
    @Override
    public boolean write(String fileName, String data) {
        if (data.isEmpty()) {
            throw new RuntimeException("Invalid data. Report can't be empty");
        }
        File report = new File(fileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(report))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file " + fileName, e);
        }
        return report.exists();
    }
}
