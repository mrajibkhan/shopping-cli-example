package com.mrk.shop.services;

import static org.powermock.api.mockito.PowerMockito.when;

import com.mrk.shop.exceptions.InvalidFileException;
import com.mrk.shop.models.ShoppingCart;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ShoppingCartService.class})
public class ShoppingCartServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    Path pMock;

    @InjectMocks
    private ShoppingCartService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    String fileName = "test1";

    @Test
    public void readInputFileTest() throws Exception {
        service.setOrderInputFile(fileName);
        Stream<String> linesSteam = Arrays.asList("10 Test1", "20 Test2").stream();
        PowerMockito.mockStatic(Files.class);
        PowerMockito.mockStatic(Paths.class);
        when(Paths.get(fileName)).thenReturn(pMock);
        when(Files.lines(pMock)).thenReturn(linesSteam);

        ShoppingCart cart = service.readInputFile();

        // verify calls
        PowerMockito.verifyStatic(Paths.class);
        Paths.get(fileName);
        PowerMockito.verifyStatic(Files.class);
        Files.lines(pMock);

        assertThat("cart should contain tow items", cart.getItems().size(), is(2));
        assertThat("first item quantity should be 10", cart.getItems().get(0).getQuantity(), is(10));
        assertThat("first item product code should be Test1", cart.getItems().get(0).getProductCode(), is("Test1"));
        assertThat("second item quantity should be 20", cart.getItems().get(1).getQuantity(), is(20));
        assertThat("second item product code should be Test2", cart.getItems().get(1).getProductCode(), is("Test2"));
    }

    @Test
    public void readInputFileExceptionTest() throws Exception {
        thrown.expect(InvalidFileException.class);
        thrown.expectMessage("Couldn't read order from file. Error: java.nio.file.NoSuchFileException: test1");
        service.setOrderInputFile(fileName);
        service.readInputFile();
    }

}