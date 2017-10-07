package com.mrk.shop.views;


import com.mrk.shop.TestDataUtils;
import com.mrk.shop.models.Order;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

public class OrderViewTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    OrderView orderView;

    @Before
    public void setup() {
        Order order = TestDataUtils.createOrder();
        orderView = new OrderView(order);
        System.out.println(orderView);
    }

    @Test
    public void printTest() {
        orderView.print();
        String output = this.outputCapture.toString();
        Assertions.assertThat(output).contains("Total: $130.00");
        Assertions.assertThat(output).contains("50 testCode1 $65");
        Assertions.assertThat(output).contains("5 x 9 $12.00");
        Assertions.assertThat(output).contains("1 x 5 $5.00");
        Assertions.assertThat(output).contains("50 testCode2 $65");
        Assertions.assertThat(output).contains("5 x 9 $12.00");
        Assertions.assertThat(output).contains("1 x 5 $5.00");
    }

}