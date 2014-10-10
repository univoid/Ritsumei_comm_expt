/**
 *
 * UnicastReceiver.java
 *
 */
import java.net.*;
import java.io.*;

public class UnicastReceiver{
   static final int echo_PORT = 50000;
   static final int BUFSIZE = 1792;
   public static void main(String[] args) throws IOException{
      int port = echo_PORT;
      int cliport;
      int len;
      int i;
      DatagramSocket sock = null;  /
      byte[] buf = new byte[BUFSIZE];
      byte[] wbuf = new byte[BUFSIZE];
      String recvdata;
      try{
         sock = new DatagramSocket(port);
         DatagramPacket recvpacket = new DatagramPacket(buf,BUFSIZE);
         DatagramPacket sendpacket = new DatagramPacket(buf,BUFSIZE);
         System.out.println("Connected to echo server");
         while(true){
            sock.receive(recvpacket);
            recvdata = new String(recvpacket.getData(),0,recvpacket.getLength());
            recvdata = recvdata.trim();
            outputStream out = new FileOutputStream("..");
            BufferedOutputStream bout = new BufferedOutputStream(out);
            //wbuf = recvdata.getBytes();
            i = 0;
            //len = wbuf.length;
            buf = new byte[BUFSIZE];
	    pkg = new DatagramPacket(buf, buf.length);

            byte[] messagebuf = new byte[1024];
            messagebuf = "ok".getBytes();
            messagepkg = new DatagramPacket(messagebuf, messagebuf.length,
                    new InetSocketAddress(ip, clientPort));
            // 循环接收包，每接到一个包后回给对方一个确认信息，对方才发下一个包(避免丢包和乱序)，直到收到一个结束包后跳出循环，结束文件传输，关闭流
            while (true) {
                receive.receive(pkg);
                if (new String(pkg.getData(), 0, pkg.getLength()).equals("end")) {
                    System.out.println("文件接收完毕");
                    bos.close();
                    receive.close();
                    break;
                }
                receive.send(messagepkg);
                System.out.println(new String(messagepkg.getData()));
                bos.write(pkg.getData(), 0, pkg.getLength());
                bos.flush();
            }
            bos.close();
            receive.close();
 //           while(wbuf[i] != 0x0d){  //rÌCRo
 //              buf[i] = wbuf[i];  //f[^Rs[
 //              i++;
 //              if(i >=len){
 //                 break;
 //              }
 //           }
 //           recvdata = new String(buf,0,i);  //oCgzñš¶ñÏ·
 //           System.out.println(recvdata);  //æÊoÍ
 //           InetAddress ipadr = recvpacket.getAddress();
 //           cliport = recvpacket.getPort();
 //           buf = recvdata.getBytes();
 //           len = buf.length;
 //           sendpacket = new DatagramPacket(buf,len,ipadr,cliport);
 //           sock.send(sendpacket);  //f[^M
 //           recvpacket.setLength(BUFSIZE);  //f[^OpPbg·Zbg
         }
      }catch(SocketException e){
         e.printStackTrace();
      }
   }
}
