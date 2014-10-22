/*
*
* java UnicastSender filename IP0 IP1 IP2 .......
*
*/

import java.io.*;
import java.net.*;

public class UnicastSender {
    static final int UNI_PORT = 55555;
    static final int BUFSIZE = 1792;

    public static void main(String[] args) {
        long start, end;                        //timer
        DatagramSocket sendSocket = null;       //socket
        thread[] th = new thread[args.length];                          //thread array
        //if (args.length > 30) {
        //    System.out.println("Sorry, this program just support 30 Clients at the same time");
        //    break;
        // }
        try {
            start = System.currentTimeMillis(); //timer begin
            sendSocket = new DatagramSocket();
	        int ipnumber = 1;
            //start Threads
            while (ipnumber < args.length) {
                th[ipnumber] = new thread(sendSocket, args[ipnumber], UNI_PORT, BUFSIZE, args[0]);
                th[ipnumber].start();
                ipnumber++;
            }
            //wait for thread stop
            try {
                ipnumber = 1;
                while (ipnumber < args.length) {
                    th[ipnumber].join();
                    ipnumber++;
                }
            } catch (InterruptedException ee) {};

            end = System.currentTimeMillis(); //timer end
            //print time
            System.out.println("time: " + ((end - start) / 1000.0) + "sec");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class thread extends Thread {
    FileInputStream in = null;              //file
    BufferedInputStream bin = null;         //bin file
    DatagramSocket socket = null;
    DatagramPacket packet = null;           //send packet
    FileInputStream fileIn = null;              //file input
    BufferedInputStream fileBin = null;         //bin file input
    String IPString;                        //String of IP
    InetAddress ip = null;                  //IP
    int port;
    int bufsize;
    String file;                             //file name
    thread(DatagramSocket socket, String IPString, int port, int bufsize, String file) {
        this.socket = socket;
        this.IPString = IPString;
        //this.ip = InetAddress.getByName(IPString);
        this.port = port;
        this.bufsize = bufsize;
        this.file = file;
    }
    public void run() {
       try{
         fileIn = new FileInputStream(file);
         fileBin = new BufferedInputStream(fileIn);
         ip = InetAddress.getByName(IPString);
         byte[] buf = new byte[bufsize];
         int i;
         //send file
         while ((i = fileBin.read(buf, 0, bufsize)) == bufsize) {
             packet = new DatagramPacket(buf, buf.length, ip, port);
             socket.send(packet);
             Thread.sleep(5);
         }
         packet = new DatagramPacket(buf, 0, i, ip, port);
         socket.send(packet);
         Thread.sleep(5);
         System.out.println("Send done for "+IPString);
         fileBin.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}



      
