/*
*
* java UnicastSender IP filename
*
*/

import java.io.*;
import java.net.*;

public class UnicastSender {
    static final int UNI_PORT = 55555;
    static final int BUFSIZE = 1792;

    public static void main(String[] args) {
        FileInputStream in = null;              //file
        BufferedInputStream bin = null;        //bin file
        DatagramSocket sendSocket = null;        //socket
        DatagramPacket sendPacket = null;        //send packet
        DatagramPacket getPacket = null;        //get packet
        InetAddress ip = null;                  //ip

        try {
            sendSocket = new DatagramSocket();
            int port = UNI_PORT;
            ip = InetAddress.getByName(args[0]);
            //input stream begin
            in = new FileInputStream(args[1]);
            bin = new BufferedInputStream(in);
            byte[] buf = new byte[BUFSIZE];
            int i;
            //send file
            while ((i = bin.read(buf, 0, BUFSIZE)) == BUFSIZE) {
                sendPacket = new DatagramPacket(buf, buf.length, ip, port);
                sendSocket.send(sendPacket);
                Thread.sleep(5);
            }
            sendPacket = new DatagramPacket(buf, 0, i, ip, port);
            sendSocket.send(sendPacket);
            Thread.sleep(5);
            System.out.println("Send done.");
            //input stream end
            bin.close();
            //get feedback message
            byte[] getBuf = new byte[BUFSIZE];
            getPacket = new DatagramPacket(getBuf, getBuf.length);
            sendSocket.receive(getPacket);
            String backMes = new String(getBuf, 0, getPacket.getLength());
            System.out.println("feefBack:" + backMes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
