package ru.mts.hw1;
/**
 *Класс для домашнего задания 1,
 * который содержит 3 поля
 * */
public class Product {
    //количество товара
    public int count;
    //сумма товара
    public double sum;
    //скидка на товар
    public double sale;

    public Product(int count, double sum, double sale) {
        this.count = count;
        this.sum = sum;
        this.sale = sale;
    }
}
