package ru.savelyev.analyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.savelyev.analyzer.service.CharacterFrequencyService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private final CharacterFrequencyService interpreterService;

    @GetMapping("/calculate-character-frequency")
    @Operation(summary = "calculateCharacterFrequency", description = "This method computes and sorts character " +
            "frequencies in a given string, arranging them in descending order of occurrence.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    public Map<Character, Integer> calculateCharacterFrequency(@RequestParam String source) {
        interpreterService.validRequestString(source);
        return interpreterService.calculateCharacterFrequency(source);
    }
}
