package ru.mts.hw1;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product(5, 100.15, 0.75);
        Product product2 = new Product(3, 15.5, 42.575);
        Product product3 = new Product(5, 40.77, 59.1);
        countSumWithSaleAndWithoutSale(product1);
        countSumWithSaleAndWithoutSale(product2);
        countSumWithSaleAndWithoutSale(product3);
    }
    public static void countSumWithSaleAndWithoutSale(Product product) {
        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println("Сумма без скидки " + df.format(product.count * product.sum));
        System.out.println("Сумма со скидкой " + df.format(product.count * product.sum * (100 - product.sale) / 100));
    }
}