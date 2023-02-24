package core.basesyntax.service;

import java.util.List;

public interface ParserService {
    List<List<String>> parseInput(String dataFromCsv);
}
