package ru.savelyev.analyzer.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.savelyev.analyzer.exceptions.StringLengthViolenceException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CharacterFrequencyService {
    @Value("${string.length}")
    private int length;
    private String stringErrorMessage;

    @PostConstruct
    public void initLengthMessage() {
        stringErrorMessage = String.format("String  length shouldn't  be blank or more than %d symbols ", length);
    }
    public Map<Character, Integer> calculateCharacterFrequency(String source) {
        log.info("Analyzing source string - " + source);
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            map.merge(c, 1, Integer::sum);
        }
        return
                map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    public void validRequestString(String source) {
        if (source.isBlank() || source.length() > length) {
            throw new StringLengthViolenceException(stringErrorMessage);
        }
    }
}
