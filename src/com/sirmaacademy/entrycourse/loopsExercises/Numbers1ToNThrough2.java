package com.sirmaacademy.entrycourse.loopsExercises;

import java.util.Scanner;

public class Numbers1ToNThrough2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= N ; i+=2) {
            System.out.println(i);
        }
    }
}
