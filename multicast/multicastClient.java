/**
 *
 * multicast�N���C�A���g
 * multicastClient.java
 *
 * �p�@��
 * >java multicastClient 224.0.1.1
 */
import java.io.*;
import java.net.*;

class multicastClient{
   static final int MULTI_PORT = 50000;  //�}���`�L���X�g�ڑ��p�|�[�g�ԍ�
   static final int BUFSIZE = 1792;  //�o�b�t�@�T�C�Y
   public static void main(String[] args)
   {
      int len;  //�f�[�^��
      int i;  //�L�[�C���o�b�t�@�N���A�C���f�b�N�X
      byte[] wbuf = new byte[BUFSIZE];  //���[�N�o�b�t�@
      MulticastSocket sock = null;  //�}���`�L���X�g�\�P�b�g
      InetAddress ipadr;  //�}���`�L���X�gIP�A�h���X
      //new begin
      FileOutputStream out = null;		//file
      BufferedOutputStream bout = null;	//bin file
      DatagramPacket getPacket = null;	//get packet
      //new end
      if(args.length != 1){
         throw new IllegalArgumentException(
         "usage: >java multicastClient ��IP_Address��");
      }
      try
      {
         //�}���`�L���X�g�\�P�b�g�ݒ�
         sock = new MulticastSocket(MULTI_PORT);
         //IP�A�h���X�ݒ�
         ipadr = InetAddress.getByName(args[0]);
         sock.joinGroup(ipadr);
         byte[] buf = new byte[BUFSIZE];  //��M�o�b�t�@
         while(true){
            //�f�[�^�O�����p�P�b�g
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            sock.receive(packet);  //�f�[�^��M
            String recvdata = new String(packet.getData());
            recvdata = recvdata.trim();
            wbuf = recvdata.getBytes();  //�����񁨃o�C�g�z��ϊ�
            i = 0;
            len = wbuf.length;
            buf = new byte[BUFSIZE];
            while(wbuf[i] != 0x0d){  //�r����CR���o
               buf[i] = wbuf[i];  //�f�[�^�R�s�[
               i++;
               if(i >= len){
                  break;
               }
            }
            recvdata = new String(buf,0,i);  //�o�C�g�z�񁨕�����ϊ�
            if("QUIT".toString().equalsIgnoreCase(recvdata)){
               break;
            }
            System.out.println("Get:"+recvdata);  //��ʏo��
	    //new begin
	    out = new FileOutputStream(recvdata);
	    bout = new BufferedOutputStream(out);
	    //byte[] buf = new byte[BUFSIZE];
            i=0;
	    //get file
            while(true){
                getPacket = new DatagramPacket(buf, 0, buf.length);
                sock.receive(getPacket);
                i = getPacket.getLength();
                bout.write(buf,0,i);
                if(i<BUFSIZE){
                    break;
                }
            }
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
