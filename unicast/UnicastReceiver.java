/*
*
* >java UnicastReceiver filename
*
*/

import java.io.*;
import java.net.*;

public class UnicastReceiver {
    static final int UNI_PORT = 55555;
    static final int BUFSIZE = 1792;

    public static void main(String[] args) {
        FileOutputStream out = null;        //file
        BufferedOutputStream bout = null;    //bin file
        DatagramSocket serverSocket = null;    //socket
        DatagramPacket getPacket = null;    //get packet
        DatagramPacket sendPacket = null;    //send packet

        try {
            System.out.println("Server start.");
            int port = UNI_PORT;
            //output stream begin
            serverSocket = new DatagramSocket(port);
            out = new FileOutputStream(args[0]);
            bout = new BufferedOutputStream(out);
            byte[] buf = new byte[BUFSIZE];
            int i = 0;              //length of Packet
            //get file
            while (true) {
                getPacket = new DatagramPacket(buf, 0, buf.length);
                serverSocket.receive(getPacket);
                i = getPacket.getLength();
                bout.write(buf, 0, i);
                if (i < BUFSIZE) {
                    break;
                }
            }
            System.out.println("Get done.");
            //output stream end
            bout.close();
            //get feedback
            SocketAddress sendAddress = getPacket.getSocketAddress();
            String feedback = "received successfully!";
            byte[] backBuf = feedback.getBytes();
            sendPacket = new DatagramPacket(backBuf, backBuf.length, sendAddress);
            serverSocket.send(sendPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
