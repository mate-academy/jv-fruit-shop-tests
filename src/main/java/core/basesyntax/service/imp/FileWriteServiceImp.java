package core.basesyntax.service.imp;

import core.basesyntax.service.FileWriteService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriteServiceImp implements FileWriteService {
    @Override
    public void writeCsvToFile(List<String> text, String path) {
        checkValidText(text);
        File file = new File(path);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String str : text) {
                bufferedWriter.write(str);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write file" + path, e);
        }
    }

    private void checkValidText(List<String> text) {
        if (text == null) {
            throw new NullPointerException("Can't write null data to file");
        }
    }
}
