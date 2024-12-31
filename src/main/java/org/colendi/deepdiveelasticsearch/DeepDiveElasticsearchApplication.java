package org.colendi.deepdiveelasticsearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.colendi.deepdiveelasticsearch.model.ProductModel;
import org.colendi.deepdiveelasticsearch.repository.ProductModelRepository;
import org.colendi.deepdiveelasticsearch.usecase.AutoCompleteUseCaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("org.colendi.deepdiveelasticsearch.config"),
        @ComponentScan("org.colendi.deepdiveelasticsearch.model"),
        @ComponentScan("org.colendi.deepdiveelasticsearch.usecase"),
        @ComponentScan("org.colendi.deepdiveelasticsearch.adapter"),
        @ComponentScan("org.colendi.deepdiveelasticsearch.repository")
})
public class DeepDiveElasticsearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DeepDiveElasticsearchApplication.class, args);
    }

    @Autowired
    private AutoCompleteUseCaseHandler autoCompleteUseCaseHandler;
    @Autowired
    private ProductModelRepository productModelRepository;
    @Override
    public void run(String... args) throws Exception {
        //uploadFromCsvToElasticSearch();
        autoCompleteUseCaseHandler.autoComplete("Gam")
                .forEach(System.out::println);
    }


    public void uploadFromCsvToElasticSearch() throws IOException {
        String path = "/Users/furkanozmen/Desktop/carstuido/backend/deep-dive-elasticsearch/src/main/resources/product-Table 1.csv";

        InputStream inputStream = new FileInputStream(path);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                .withDelimiter(';') // Ayırıcı olarak ';' kullan
                .withFirstRecordAsHeader()); // İlk satırı başlık olarak al

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            ProductModel productModel = ProductModel.builder()
                    .stockCode(csvRecord.get("StockCode")) // Başlık adıyla sütunu al
                    .productName(csvRecord.get("Product Name"))
                    .description(csvRecord.get("Description"))
                    .category(csvRecord.get("Category"))
                    .brand(csvRecord.get("Brand"))
                    .unitPrice(formatPrice(csvRecord.get("Unit Price")))
                    .build();

            productModelRepository.save(productModel);
        }

        csvParser.close();
        fileReader.close();
    }

    private String formatPrice(String price) {
        if (price != null) {
            return price.replace(",", ".");
        }
        return null;
    }

}
