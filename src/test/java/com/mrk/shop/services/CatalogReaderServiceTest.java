package com.mrk.shop.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mrk.shop.exceptions.InvalidFileException;
import com.mrk.shop.models.ProductCatalog;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CatalogReaderServiceTest {

    String catalogFileValid = "src/test/resources/test-catalog.yaml";
    String catalogFileInvalid = "src/test/resources/test-catalog-invalid.yaml";

    CatalogReaderService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readProductCatalogSuccessTest() throws Exception {
        service = new CatalogReaderService(catalogFileValid, new ObjectMapper(new YAMLFactory()));
        ProductCatalog catalog = service.readProductCatalog();
        System.out.println(catalog);
        assertThat("catalog should not be null", catalog, notNullValue());
        assertThat("should contain 3 items", catalog.getCatalogItems().size(), is(3));
    }

    @Test
    public void readProductCatalogFileNotFoundTest() throws Exception {
        thrown.expect(FileNotFoundException.class);
        service = new CatalogReaderService("noFile.yaml", new ObjectMapper(new YAMLFactory()));
        service.readProductCatalog();
    }

    @Test
    public void readProductCatalogInvalidContentTest() throws Exception {
        thrown.expect(InvalidFileException.class);
        service = new CatalogReaderService(catalogFileInvalid, new ObjectMapper(new YAMLFactory()));
        service.readProductCatalog();
    }

}