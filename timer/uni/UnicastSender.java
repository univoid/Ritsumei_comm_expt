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
        FileInputStream in = null;              //file
        BufferedInputStream bin = null;         //bin file
        DatagramSocket sendSocket = null;       //socket
        DatagramPacket sendPacket = null;       //send packet
        DatagramPacket getPacket = null;        //get packet
        InetAddress ip = null;                  //ip

        try {
            start = System.currentTimeMillis(); //timer begin
            sendSocket = new DatagramSocket();
            int port = UNI_PORT;
            //input stream begin
            in = new FileInputStream(args[0]);
            bin = new BufferedInputStream(in);
            byte[] buf = new byte[BUFSIZE];
            int i;
            //send file
            int ipnumber;               //Receiver IP's number
            while ((i = bin.read(buf, 0, BUFSIZE)) == BUFSIZE) {
                ipnumber = 1;
                while (ipnumber < args.length) {
                    ip = InetAddress.getByName(args[ipnumber]);
                    sendPacket = new DatagramPacket(buf, buf.length, ip, port);
                    sendSocket.send(sendPacket);
                    Thread.sleep(5);
                    ipnumber++;
                }
            }
            ipnumber = 1;
            while (ipnumber < args.length) {
                ip = InetAddress.getByName(args[ipnumber]);
                sendPacket = new DatagramPacket(buf, 0, i, ip, port);
                sendSocket.send(sendPacket);
                Thread.sleep(5);
                ipnumber++;
            }
            System.out.println("Send done.");
            //input stream end
            bin.close();
            //get feedback message
            byte[] getBuf = new byte[BUFSIZE];
            getPacket = new DatagramPacket(getBuf, getBuf.length);
            sendSocket.receive(getPacket);
            String backMes = new String(getBuf, 0, getPacket.getLength());
            System.out.println("feefBack:" + backMes);
            end = System.currentTimeMillis(); //timer end
            //print time
            System.out.println("time: " + ((end - start) / 1000.0) + "sec");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
