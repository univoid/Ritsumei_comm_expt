/**
 * multicastServer.java
 *
 *
 * > java multicastServer 224.0.1.1
 * > send> filename
 */
import java.io.*;
import java.net.*;

class multicastServer{
   static final int MULTI_PORT = 50000;
   static final int BUFSIZE = 1792;
   public static void main(String[] args)
   {
      MulticastSocket sock = null;
      InetAddress ipadr = null;
      keyinCheck key =null;
      byte TTL = (byte)1;
      byte[] buf = new byte[BUFSIZE];
      int len;
      int k;

      //new begin
      FileInputStream in = null;
      BufferedInputStream bin= null;
      //DatagramSocket sendSocket= null;        //socket
      DatagramPacket sendPacket =null;        //send packet
      //new end
      if(args.length != 1){
         throw new IllegalArgumentException(
                   "usage: >java multicastServer <IP_Adress>");
      }
      try
      {

         ipadr = InetAddress.getByName(args[0]);
         while(true){
            System.out.print("send>");
	        k = System.in.read(buf);
            len = buf.length;

            DatagramPacket packet = new DatagramPacket(
                                        buf,len,ipadr,MULTI_PORT);
            int port = MULTI_PORT;
            sock = new MulticastSocket();
            sock.setTimeToLive(TTL);
            sock.joinGroup(ipadr);
            sock.send(packet);
            //new begin
            //TODO CIL read filename
            byte[] wbuf = new byte[BUFSIZE];
            int i = 0;
            while (buf[i]!= 0x0d){
                wbuf[i] = buf[i];
                i++;
                if(i >= len){
                     break;
                }
            }
            String fileName = new String(wbuf, 0, i);
            key = new keyinCheck();
            if(key.quitCheck(buf,k) == 1){
                sock.leaveGroup(ipadr);
                break;
            }
            in = new FileInputStream(fileName);
            bin = new BufferedInputStream(in);
            buf =new byte[BUFSIZE];
            i = 0;
            //send filename
            while ((i = bin.read(buf, 0, BUFSIZE) )==BUFSIZE){
            sendPacket = new DatagramPacket(buf, buf.length, ipadr, port); 		    sock.send(sendPacket);
            Thread.sleep(5);
            }
            sendPacket = new DatagramPacket(buf, 0, i, ipadr, port);
            sock.send(sendPacket);
            Thread.sleep(5);
            System.out.println("send done.");
            //new end
         }
         sock.close();
         while(true){
         }
      }catch(java.net.SocketException e){
         System.err.println(e);
      }catch(java.io.IOException e){
         System.err.println(e);
      }catch (Exception e){
            e.printStackTrace();
      }

   }
}

class keyinCheck{
   private int QUIT;
   private int RET;
   keyinCheck(){
      QUIT = 1;
      RET = 0;
   }
   // quit check
   public int quitCheck(byte buf[], int count){
      String keyinstr = new String(buf,0,count);
      if(keyinstr.equals("quit\r\n") || keyinstr.equals("QUIT\r\n")) {
         System.out.println("Quit  ([Ctrl]+[C])");
         return QUIT;
      }
      return RET;
   }
}
