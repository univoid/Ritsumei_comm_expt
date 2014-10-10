/**

 *
 * �p�@��
 * >java UnicastSender 192.168.1.1 data.txt
 */
import java.net.*;
import java.io.*;

public class UnicastSender{
   static final int echo_PORT = 7;  //�G�R�[�|�[�g�ԍ�
   static final int BUFSIZE = 1792;  //�o�b�t�@�T�C�Y

   public static void main(String[] args) throws IOException{
      int port = echo_PORT;  //�|�[�g�ԍ�
      //byte[] buf = new byte[BUFSIZE];  //����M�o�b�t�@
      String fileName;  //file name
      DatagramSocket sock = null;  //�f�[�^�O�����\�P�b�g�錾�C������
      if(args.length != 1){
         throw new IllegalArgumentException("usage: >java  UnicastSender��IP_Address��");
      }
      while(true){
         try{
            InetAddress adr = InetAddress.getByName(args[0]);  //�T�[�oIP�A�h���X
            //System.out.print("cli>");  //�v�����v�g�o��
            fileName = args[1];  //�L�[����

	        //input begin
	        InputStream in = new FileInputStream(fileName);  //���̓t�@�C�����J��
            //BufferedInputStream�I�u�W�F�N�g�̐���
            BufferedInputStream bin = new BufferedInputStream(in);
            byte buf[] = new byte[BUFSIZE];
            int c;
	        sock =new DatagramSocket();
            //�ǂݍ��݃f�[�^���Ȃ��Ȃ�܂œǂݍ���
            while((c = bin.read(buf)) != -1){
               DatagramPacket packet = new DatagramPacket(buf,c,adr,port);
               sock.send(packet);  //���̓f�[�^���M
               sock.receive(packet);  //�G�R�[�f�[�^��M
            }
	        //input end
	        //
            //sock = new DatagramSocket();
            //DatagramPacket packet = new DatagramPacket(buf,buf.length,adr,port);
            //sock.send(packet);  //���̓f�[�^���M
            //buf = new byte[BUFSIZE];
            //packet = new DatagramPacket(buf,buf.length);
            //sock.receive(packet);  //�G�R�[�f�[�^��M
            //String data = new String(packet.getData(),0,packet.getLength());
            //System.out.println(data);  //��ʏo��
	        bin.close();  //���̓t�@�C�������
            sock.close();
         }catch(SocketException e){
            e.printStackTrace();
         }
      }
   }
}
