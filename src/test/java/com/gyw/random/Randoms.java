package com.gyw.random;

import java.util.Random;
import java.util.Scanner;

/**
 * @author 高源蔚
 * @date 2021/12/18-16:48
 * @describe
 */
public class Randoms {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("请输入");
            int i1 = sc.nextInt();
            if(i1 == 1){
                int i = random.nextInt(2);
                if(i==0){
                    System.out.println("不吃");
                }else {
                    System.out.println("吃");
                }
            }else {
                break;
            }
        }
        System.out.println("结束");
    }
}


