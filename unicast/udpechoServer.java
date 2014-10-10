/**
 *
 * udpecho�T�[�o
 * udpechoServer.java
 *
 * �p�@��
 * >java udpechoServer
 */
import java.net.*;
import java.io.*;

public class udpechoServer{
   static final int echo_PORT = 7;  //�G�R�[�|�[�g�ԍ�
   static final int BUFSIZE = 1792;  //�o�b�t�@�T�C�Y

   public static void main(String[] args) throws IOException{
      int port = echo_PORT;  //�|�[�g�ԍ�
      int cliport;  //�N���C�A���g�|�[�g�ԍ�
      int len;  //�f�[�^��
      int i;  //�L�[�C���o�b�t�@�N���A�C���f�b�N�X
      DatagramSocket sock = null;  //�f�[�^�O�����\�P�b�g�錾�C������
      byte[] buf = new byte[BUFSIZE];
      byte[] wbuf = new byte[BUFSIZE];
      String recvdata;  //��M�f�[�^
      try{
         sock = new DatagramSocket(port);
         DatagramPacket recvpacket = new DatagramPacket(buf,BUFSIZE);
         DatagramPacket sendpacket = new DatagramPacket(buf,BUFSIZE);
         System.out.println("Connected to echo server");
         while(true){
            sock.receive(recvpacket);  //�f�[�^��M
            recvdata = new String(recvpacket.getData(),0,recvpacket.getLength());
            recvdata = recvdata.trim();  //�g���~���O(CRLF)
            wbuf = recvdata.getBytes();  //�����񁨃o�C�g�z��ϊ�
            i = 0;
            len = wbuf.length;
            buf = new byte[BUFSIZE];
            while(wbuf[i] != 0x0d){  //�r����CR���o
               buf[i] = wbuf[i];  //�f�[�^�R�s�[
               i++;
               if(i >=len){
                  break;
               }
            }
            recvdata = new String(buf,0,i);  //�o�C�g�z�񁨕�����ϊ�
            System.out.println(recvdata);  //��ʏo��
            InetAddress ipadr = recvpacket.getAddress();
            cliport = recvpacket.getPort();
            buf = recvdata.getBytes();
            len = buf.length;
            sendpacket = new DatagramPacket(buf,len,ipadr,cliport);
            sock.send(sendpacket);  //�f�[�^���M
            recvpacket.setLength(BUFSIZE);  //�f�[�^�O�����p�P�b�g�����Z�b�g
         }
      }catch(SocketException e){
         e.printStackTrace();
      }
   }
}
