/*
*
* java UnicastSender IP filename
*
*/
import java.io.*;
import java.net.*;

public class UnicastSender {
    static final int uni_PORT = 55555;  
    static final int BUFSIZE = 1792;
    public static void main(String[] args) {
        FileInputStream in = null;              //file
	BufferedInputStream bin= null;		//bin file
        DatagramSocket sendSocket= null;        //socket
        DatagramPacket sendPacket =null;        //send packet
        DatagramPacket getPacket = null;        //get packet
        InetAddress ip = null;                  //ip

        try {
            sendSocket = new DatagramSocket();
            int port = uni_PORT;
            ip = InetAddress.getByName(args[0]);
            in = new FileInputStream(args[1]);
	    bin = new BufferedInputStream(in);
            byte[] buf =new byte[BUFSIZE];
            int i;
	    //send file
            while((i = bin.read(buf, 0, BUFSIZE) )==BUFSIZE){
                sendPacket = new DatagramPacket(buf, buf.length, ip,  port);
                sendSocket.send(sendPacket);
                Thread.sleep(5);
            }
            sendPacket = new DatagramPacket(buf, 0, i, ip, port);
            sendSocket.send(sendPacket);
            Thread.sleep(5);
            System.out.println("Send done.");
	    //get feedback message
            byte[] getBuf = new byte[BUFSIZE];
            getPacket = new DatagramPacket(getBuf, getBuf.length);
            sendSocket.receive(getPacket);
            String backMes = new String(getBuf, 0, getPacket.getLength());
            System.out.println("feefBack:"+backMes);

	    bin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
