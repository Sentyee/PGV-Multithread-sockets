/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.tools.javac.util.Convert;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author tibur
 */
public class ClientCommunication extends Thread {

    Socket socket;

    public ClientCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        serveClient(socket);
    }

    public static int getRandom(int[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    public static String getRandomOperation(String[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    private static void serveClient(Socket socket) {
        BufferedReader br = null;
        int firstNumber = 0;
        int secondNumber = 0;
        int result = 0;
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] operations = new String[]{"+", "-", "x"};

        String operation = "";

        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            int line = 0;
            String lineToSendToClient;

            do {
                
                firstNumber = getRandom(numbers);
                secondNumber = getRandom(numbers);
                operation = getRandomOperation(operations);

                switch (operation) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;

                    case "-":
                        result = firstNumber - secondNumber;
                        break;

                    case "x":
                        result = firstNumber * secondNumber;
                        break;
                }
                
                line = 0;
                
                lineToSendToClient = "Resuelve esta operación: " + String.valueOf(firstNumber) + " " + operation + " " + String.valueOf(secondNumber);

                bw.write(lineToSendToClient);
                bw.newLine();
                bw.flush();
                
                line = Integer.parseInt(br.readLine());

                if (line == result) {
                    lineToSendToClient = "ACERTASTE!";
                    System.out.println(result);

                } else {
                    lineToSendToClient = "FALLASTE!";
                    System.out.println(result);
                }

                bw.write(lineToSendToClient);
                bw.newLine();
                bw.flush();

            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
