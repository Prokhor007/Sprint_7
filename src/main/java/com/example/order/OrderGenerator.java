package com.example.order;

public class OrderGenerator {
    public static OrderCreate orderWithGrey(){
        return new OrderCreate("Иван","Иванов","Москва, Ленина 1","Спортивная", "+78000000001",  "1","2023-11-11","Привезти побыстрее", new String[]{"GREY"});
    }

    public static OrderCreate orderWithBlack(){
        return new OrderCreate("Иван","Иванов","Москва, Ленина 1","Спортивная", "+78000000001",  "1","2023-11-11","Привезти побыстрее", new String[]{"BLACK"});
    }

    public static OrderCreate orderWithoutColour(){
        return new OrderCreate("Иван","Иванов","Москва, Ленина 1","Спортивная", "+78000000001",  "1","2023-11-11","Привезти побыстрее",null);
    }

    public static OrderCreate orderWithTwoColours(){
        return new OrderCreate("Иван","Иванов","Москва, Ленина 1","Спортивная", "+78000000001",  "1","2023-11-11","Привезти побыстрее", new String[]{"GREY", "BLACK"});
    }
}
