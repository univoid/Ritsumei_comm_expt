/*
 *
 * > java MulticastServer multiIP
 * > send> filename
 *
 */

import java.io.*;
import java.net.*;

public class MulticastServer {
    static final int MULTI_PORT = 50000;
    static final int BUFSIZE = 1792;

    public static void main(String[] args) {
        long start, end;                        //timer
        FileInputStream in = null;
        BufferedInputStream bin = null;
        MulticastSocket sock = null;
        DatagramPacket sendPacket = null;
        InetAddress ipadr = null;
        KeyinCheck key = null;
        byte TTL = (byte) 1;
        byte[] buf = new byte[BUFSIZE];
        byte[] wbuf = new byte[BUFSIZE];        //buf for filename words
        int len;
        int k;

        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "usage: >java multicastServer <IP_Adress>");
        }
        try {
            ipadr = InetAddress.getByName(args[0]);
            while (true) {
                System.out.print("send>");
                k = System.in.read(wbuf);
                len = wbuf.length;
                DatagramPacket packet = new DatagramPacket(
                        wbuf, len, ipadr, MULTI_PORT);
                int port = MULTI_PORT;
                sock = new MulticastSocket();
                sock.setTimeToLive(TTL);
                sock.joinGroup(ipadr);
                sock.send(packet);
                //CIL read filename
                int i = 0;
                while (wbuf[i] != 0x0d && wbuf[i] != '\n') {
                    buf[i] = wbuf[i];
                    i++;
                    if (i >= len) {
                        break;
                    }
                }
                String fileName = new String(buf, 0, i);
                key = new KeyinCheck();
                if (key.quitCheck(fileName) == 1) {
                    sock.leaveGroup(ipadr);
                    break;
                }
                //input stream begin
                start = System.currentTimeMillis(); //timer begin
                in = new FileInputStream(fileName);
                bin = new BufferedInputStream(in);
                buf = new byte[BUFSIZE];
                i = 0;
                //send file
                while ((i = bin.read(buf, 0, BUFSIZE)) == BUFSIZE) {
                    sendPacket = new DatagramPacket(buf, buf.length, ipadr, port);
                    sock.send(sendPacket);
                    Thread.sleep(5);
                }
                sendPacket = new DatagramPacket(buf, 0, i, ipadr, port);
                sock.send(sendPacket);
                Thread.sleep(5);
                System.out.println("send done.");
                //input stream end
                bin.close();
                end = System.currentTimeMillis(); //timer end
                //print time
                System.out.println("time: " + ((end - start) / 1000.0) + "sec");

            }
            sock.close();
            while (true) {
            }
        } catch (java.net.SocketException e) {
            System.err.println(e);
        } catch (java.io.IOException e) {
            System.err.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class KeyinCheck {
    private int QUIT;
    private int RET;
    KeyinCheck() {
        QUIT = 1;
        RET = 0;
    }
    // quit check
    public int quitCheck(String keyinstr) {
        if (keyinstr.equals("quit") || keyinstr.equals("QUIT")) {
            System.out.println("Quit  ([Ctrl]+[C])");
            return QUIT;
        }
        return RET;
    }
}
