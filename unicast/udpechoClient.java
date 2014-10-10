/**
 *
 * udpecho�N���C�A���g
 * udpechoClient.java
 *
 * �p�@��
 * >java udpechoClient 192.168.1.1
 */
import java.net.*;
import java.io.*;

public class udpechoClient{
   static final int echo_PORT = 7;  //�G�R�[�|�[�g�ԍ�
   static final int BUFSIZE = 1792;  //�o�b�t�@�T�C�Y

   public static void main(String[] args) throws IOException{
      int port = echo_PORT;  //�|�[�g�ԍ�
      byte[] buf = new byte[BUFSIZE];  //����M�o�b�t�@
      int k;  //�o�C�g��
      DatagramSocket sock = null;  //�f�[�^�O�����\�P�b�g�錾�C������
      if(args.length != 1){
         throw new IllegalArgumentException("usage: >java udpechoClient ��IP_Address��");
      }
      while(true){
         try{
            InetAddress adr = InetAddress.getByName(args[0]);  //�T�[�oIP�A�h���X
            System.out.print("cli>");  //�v�����v�g�o��
            k = System.in.read(buf);  //�L�[����
            if(k == -1){
               break;
            }
            sock = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf,buf.length,adr,port);
            sock.send(packet);  //���̓f�[�^���M
            buf = new byte[BUFSIZE];
            packet = new DatagramPacket(buf,buf.length);
            sock.receive(packet);  //�G�R�[�f�[�^��M
            String data = new String(packet.getData(),0,packet.getLength());
            System.out.println(data);  //��ʏo��
         }catch(SocketException e){
            e.printStackTrace();
         }
      }
      sock.close();  //�\�P�b�g�N���[�Y
   }
}
