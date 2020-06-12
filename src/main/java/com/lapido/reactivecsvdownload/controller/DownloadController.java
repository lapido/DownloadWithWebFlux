package com.lapido.reactivecsvdownload.controller;

import com.lapido.reactivecsvdownload.service.CsvWriterService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public class DownloadController {
    private final CsvWriterService csvWriterService;

    public DownloadController(CsvWriterService csvWriterService){
        this.csvWriterService = csvWriterService;
    }

    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<Mono<Resource>> downloadCsv() {
        String fileName = String.format("%s.csv", RandomStringUtils.randomAlphabetic(10));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(csvWriterService.generateCsv()
                        .flatMap(x -> {
                            Resource resource = new InputStreamResource(x);

                            return Mono.just(resource);
                        }));
    }

}
