/**
 *
 * udpechoサーバ
 * udpechoServer.java
 *
 * 用法例
 * >java udpechoServer
 */
import java.net.*;
import java.io.*;

public class udpechoServer{
   static final int echo_PORT = 7;  //エコーポート番号
   static final int BUFSIZE = 1792;  //バッファサイズ

   public static void main(String[] args) throws IOException{
      int port = echo_PORT;  //ポート番号
      int cliport;  //クライアントポート番号
      int len;  //データ長
      int i;  //キーインバッファクリアインデックス
      DatagramSocket sock = null;  //データグラムソケット宣言，初期化
      byte[] buf = new byte[BUFSIZE];
      byte[] wbuf = new byte[BUFSIZE];
      String recvdata;  //受信データ
      try{
         sock = new DatagramSocket(port);
         DatagramPacket recvpacket = new DatagramPacket(buf,BUFSIZE);
         DatagramPacket sendpacket = new DatagramPacket(buf,BUFSIZE);
         System.out.println("Connected to echo server");
         while(true){
            sock.receive(recvpacket);  //データ受信
            recvdata = new String(recvpacket.getData(),0,recvpacket.getLength());
            recvdata = recvdata.trim();  //トリミング(CRLF)
            wbuf = recvdata.getBytes();  //文字列→バイト配列変換
            i = 0;
            len = wbuf.length;
            buf = new byte[BUFSIZE];
            while(wbuf[i] != 0x0d){  //途中のCR検出
               buf[i] = wbuf[i];  //データコピー
               i++;
               if(i >=len){
                  break;
               }
            }
            recvdata = new String(buf,0,i);  //バイト配列→文字列変換
            System.out.println(recvdata);  //画面出力
            InetAddress ipadr = recvpacket.getAddress();
            cliport = recvpacket.getPort();
            buf = recvdata.getBytes();
            len = buf.length;
            sendpacket = new DatagramPacket(buf,len,ipadr,cliport);
            sock.send(sendpacket);  //データ送信
            recvpacket.setLength(BUFSIZE);  //データグラムパケット長リセット
         }
      }catch(SocketException e){
         e.printStackTrace();
      }
   }
}
