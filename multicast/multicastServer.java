/**
 *
 * multicast�T�[�o
 * multicastServer.java
 *
 * �p�@��
 * > java multicastServer 224.0.1.1
 * > send> filename
 */
import java.io.*;
import java.net.*;

class multicastServer{
   static final int MULTI_PORT = 50000;  //�}���`�L���X�g�ڑ��p�|�[�g�ԍ�
   static final int BUFSIZE = 1792;  //�o�b�t�@�T�C�Y
   public static void main(String[] args)
   {
      MulticastSocket sock = null;  //�}���`�L���X�g�\�P�b�g
      InetAddress ipadr = null;  //IP�A�h���X
      keyinCheck key =null;  //�L�[�C���`�F�b�N
      byte TTL = (byte)1;  //TTL
      byte[] buf = new byte[BUFSIZE];  //���M�o�b�t�@
      int len;  //buf��
      int k;  //�o�C�g��

      //new begin
      FileInputStream in = null;              //file
      BufferedInputStream bin= null;		//bin file
     // DatagramSocket sendSocket= null;        //socket
      DatagramPacket sendPacket =null;        //send packet
      //new end
      if(args.length != 1){
         throw new IllegalArgumentException(
                   "usage: >java multicastServer ��IP_Adress��");
      }
      try
      {
         //IP�A�h���X�ݒ�
         ipadr = InetAddress.getByName(args[0]);
         while(true){
            System.out.print("send>");
	    k = System.in.read(buf);  // �L�[����
            len = buf.length;
            // �f�[�^�O�����p�P�b�g�ݒ�
            DatagramPacket packet = new DatagramPacket(
                                        buf,len,ipadr,MULTI_PORT);
            int port = MULTI_PORT;
	    sock = new MulticastSocket();
            sock.setTimeToLive(TTL);
            sock.joinGroup(ipadr);
            sock.send(packet); //�f�[�^���M
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
	    in = new FileInput Stream(fileName);
	    bin = new BufferedInputStream(in);
	    byte[] buf =new byte[BUFSIZE];
	    i = 0;
	    //send file
	    while ((i = bin.read(buf, 0, BUFSIZE) )==BUFSIZE){
		sendPacket = new DatagramPacket(buf, buf.length, ipadr, port); 		    sock.send(sendPacket);
		thread.sleep(5);
	    }
	    sendPacket = new DatagramPacket(buf, 0, i, ipadr, port);
	    sock.send(sendPacket);
	    thread.sleep(5);
	    System.out.println("Send done.");
	    //new end
            sock.leaveGroup(ipadr);
            key = new keyinCheck();
            if(key.quitCheck(buf,k) == 1){
               break;
            }
         }
         sock.close();
         while(true){
         }
      }catch(java.net.SocketException e){
         System.err.println(e);
      }catch(java.io.IOException e){
         System.err.println(e);
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
   // quit�`�F�b�N
   public int quitCheck(byte buf[], int count){
      String keyinstr = new String(buf,0,count);
      if(keyinstr.equals("quit\r\n") || keyinstr.equals("QUIT\r\n")) {
         System.out.println("�ڑ��f���Ă�������" + "([Ctrl]+[C]�L�[����)");
         return QUIT;
      }
      return RET;
   }
}