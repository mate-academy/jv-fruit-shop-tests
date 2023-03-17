package core.basesyntax.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import core.basesyntax.service.ReaderService;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> read(File inputFileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File " + inputFileName + " can't be read!", e);
        }
        return list;
    }
}
