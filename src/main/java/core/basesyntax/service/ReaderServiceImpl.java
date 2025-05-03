package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceImpl implements ReaderService {
    private static final String SPLIT = ",";

    @Override
    public List<String[]> read(String fileName) {
        List<String> informationFromFile = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader((new FileReader(fileName)))) {
            String text = bufferedReader.readLine();
            while (text != null) {
                informationFromFile.add(text);
                text = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read information from file " + fileName, e);
        }
        if (informationFromFile.isEmpty()) {
            throw new RuntimeException("File are empty " + fileName);
        }
        return informationFromFile.stream()
                .map(e -> e.split(SPLIT))
                .collect(Collectors.toList());
    }
}
