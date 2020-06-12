package com.lapido.reactivecsvdownload.service;

import com.lapido.reactivecsvdownload.model.User;
import com.lapido.reactivecsvdownload.util.ByteArrayInOutStream;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


@Service
public class CsvWriterService {

    public Mono<ByteArrayInputStream> generateCsv(){
        String[] columns = {"Name", "Email", "Country"};

        List<User> users = new ArrayList<>();
        users.add(new User("Adewale Joseph", "adewale.joseph@example.com", "UK"));
        users.add(new User("Adam Shaw", "adam.shaw@example.com", "DE"));
        users.add(new User("Bisi Olukoja", "bisi@example.com", "US"));

        return Mono.create(monoSink -> {
            try {
                ByteArrayInOutStream stream = new ByteArrayInOutStream();
                OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
                CSVWriter writer = new CSVWriter(streamWriter);

                ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
                mappingStrategy.setType(User.class);
                mappingStrategy.setColumnMapping(columns);
                writer.writeNext(columns);

                StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withMappingStrategy(mappingStrategy)
                        .withSeparator(',')
                        .build();

                beanToCsv.write(users);
                streamWriter.flush();
                monoSink.success(stream.getInputStream());
            }
            catch ( CsvException | IOException e) {
                System.out.println(e);
                monoSink.error(e);
            }

        }).subscribeOn(Schedulers.single())
                .cast(ByteArrayInputStream.class);

    }
}
