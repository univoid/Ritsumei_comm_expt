/**
 *
 * udpechoクライアント
 * udpechoClient.java
 *
 * 用法例
 * >java udpechoClient 192.168.1.1
 */
import java.net.*;
import java.io.*;

public class udpechoClient{
   static final int echo_PORT = 7;  //エコーポート番号
   static final int BUFSIZE = 1792;  //バッファサイズ

   public static void main(String[] args) throws IOException{
      int port = echo_PORT;  //ポート番号
      byte[] buf = new byte[BUFSIZE];  //送受信バッファ
      int k;  //バイト数
      DatagramSocket sock = null;  //データグラムソケット宣言，初期化
      if(args.length != 1){
         throw new IllegalArgumentException("usage: >java udpechoClient ＜IP_Address＞");
      }
      while(true){
         try{
            InetAddress adr = InetAddress.getByName(args[0]);  //サーバIPアドレス
            System.out.print("cli>");  //プロンプト出力
            k = System.in.read(buf);  //キー入力
            if(k == -1){
               break;
            }
            sock = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf,buf.length,adr,port);
            sock.send(packet);  //入力データ送信
            buf = new byte[BUFSIZE];
            packet = new DatagramPacket(buf,buf.length);
            sock.receive(packet);  //エコーデータ受信
            String data = new String(packet.getData(),0,packet.getLength());
            System.out.println(data);  //画面出力
         }catch(SocketException e){
            e.printStackTrace();
         }
      }
      sock.close();  //ソケットクローズ
   }
}
