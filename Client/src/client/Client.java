/*
 * To change this license header, choose License Headers in Project Properties. * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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

/**
 *
 * @author tibur
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int PORT = 40080;
        final String HOST = "192.168.1.48";

        try {
            Socket socket = new Socket(HOST, PORT);

            comunicateWithServer(socket);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void comunicateWithServer(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            Scanner sc = new Scanner(System.in);
            String lineFromServer;
            int line;

            do {
                lineFromServer = br.readLine();
                System.out.println("Server said: " + lineFromServer);

                System.out.println("Write something to send to server: ");
                line = sc.nextInt();
                bw.write(String.valueOf(line));
                bw.newLine();
                bw.flush();
                
                lineFromServer = br.readLine();
                System.out.println("Server said: " + lineFromServer);

            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
