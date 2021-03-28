package com.utn2019.avanzada2.tp9.controller;

import com.utn2019.avanzada2.tp9.domain.Phrase;
import com.utn2019.avanzada2.tp9.service.PhraseService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/phrases", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PhraseController {
    private final PhraseService productsService;

    @GetMapping()
    public ResponseEntity<List<Phrase>> getAll(@ApiParam(value = "Page index to use in conjunction with the 'size' param. Positions starts at 0.")
                                                @RequestParam(name = "page", required = false) Integer page,
                                               @ApiParam(value = "Page size to use in conjunction with the 'page' param.")
                                                @RequestParam(name = "size", required = false) Integer size,
                                               @ApiParam(value = "Allowed values: ASC & DESC.", defaultValue = "ASC")
                                                @RequestParam(name = "direction", required = false) String direction,
                                               @ApiParam(value = "Field to order by.", defaultValue = "phraseId")
                                                @RequestParam(name = "orderBy", required = false) String orderBy){
        List<Phrase> foundPrinter = productsService.getAll(page,size,direction,orderBy);
        if (foundPrinter == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundPrinter);
        }
    }
}
