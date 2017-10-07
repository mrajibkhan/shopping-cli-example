package com.mrk.shop;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void applicationTest() throws Exception {
        Application.main(new String[] {"--order.file=src/test/resources/test-order-input.txt", "--catalog.file=src/test/resources/test-catalog.yaml"});
        String output = this.outputCapture.toString();
        assertThat(output).contains("My Flower Shop");
    }

}