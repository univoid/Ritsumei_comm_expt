/**
 * multicastClient.java
 *
 * >java multicastClient 224.0.1.1
 */
import java.io.*;
import java.net.*;

class multicastClient{
   static final int MULTI_PORT = 50000;
   static final int BUFSIZE = 1792;
   public static void main(String[] args)
   {
      int len;
      int i;
      byte[] wbuf = new byte[BUFSIZE];
      MulticastSocket sock = null;
      InetAddress ipadr;
      //new begin
      FileOutputStream out = null;		//file
      BufferedOutputStream bout = null;	//bin file
      DatagramPacket getPacket = null;	//get packet
      //new end
      if(args.length != 1){
         throw new IllegalArgumentException(
         "usage: >java multicastClient <IP_Address>");
      }
      try
      {

         sock = new MulticastSocket(MULTI_PORT);

         ipadr = InetAddress.getByName(args[0]);
         sock.joinGroup(ipadr);
         byte[] buf = new byte[BUFSIZE];
         while(true){

            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            sock.receive(packet);
            String recvdata = new String(packet.getData());
            recvdata = recvdata.trim();
            wbuf = recvdata.getBytes();
            i = 0;
            len = wbuf.length;
            buf = new byte[BUFSIZE];
            while(wbuf[i] != 0x0d){
                buf[i] = wbuf[i];
                i++;
                if(i >= len){
                     break;
                }
            }
            recvdata = new String(buf,0,i);
            if("QUIT".toString().equalsIgnoreCase(recvdata)){
               break;
            }
            System.out.println("Get:"+recvdata);
	    //new begin
	    out = new FileOutputStream(recvdata);
	    bout = new BufferedOutputStream(out);
	    buf = new byte[BUFSIZE];
            i=0;
	    //get file
            while(true){
                getPacket = new DatagramPacket(buf, 0, buf.length);
                sock.receive(getPacket);
		//System.out.println(new String(getPacket.getData()));
                i = getPacket.getLength();
		//System.out.println(i);
                bout.write(buf, 0, i);
		if(i<BUFSIZE){
                    break;
                }
            }
	    bout.close();
	    System.out.println("Get done.");
	    //new end
         }
         sock.leaveGroup(ipadr);
         sock.close();
      }catch(java.io.IOException e){
         System.err.println(e);
      }
      System.out.println("\r\n..END...");
   }
}

