package com.mrk.shop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrk.shop.exceptions.InvalidFileException;
import com.mrk.shop.models.ProductCatalog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Rajib Khan on 27/09/2017
 * {@link CatalogReaderService} loads data from product catalog file and
 * creates {@link ProductCatalog}
 */

@Service @AllArgsConstructor @NoArgsConstructor
@Slf4j
public class CatalogReaderService {

    @Value("${catalog.file}")
    private String catalogFile;

    @Autowired
    @Qualifier("yamlObjectMapper")
    private ObjectMapper yamlObjectMapper;

    /**
     * Reads data from the catalog data file (yaml)
     * @return ProductCatalog
     * @throws FileNotFoundException
     * @throws InvalidFileException
     */
    public ProductCatalog readProductCatalog() throws FileNotFoundException, InvalidFileException {
        try {
            log.info("loading product catalog from file: " + catalogFile);
            return yamlObjectMapper.readValue(new File(catalogFile), ProductCatalog.class);
        } catch(FileNotFoundException fnfEx) {
            throw fnfEx;
        } catch (IOException ex) {
            throw new InvalidFileException(ex.getMessage());
        }
    }
}
