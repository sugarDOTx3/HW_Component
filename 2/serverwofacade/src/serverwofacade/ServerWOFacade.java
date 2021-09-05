/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverwofacade;

import java.util.Scanner;

public class ServerWOFacade {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        System.out.println("Please choose 1. Start, 2. Stop");
        int number = inp.nextInt();
        Start start = new Start();
        Stop stop = new Stop();
        if (number == 1) {
            start.startServer();
        } else if(number == 2){
            stop.stopServer();
        }else {
            System.out.println("No information found");
        }
    }
}
