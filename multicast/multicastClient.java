/**
 *
 * multicastクライアント
 * multicastClient.java
 *
 * 用法例
 * >java multicastClient 224.0.1.1
 */
import java.io.*;
import java.net.*;

class multicastClient{
   static final int MULTI_PORT = 50000;  //マルチキャスト接続用ポート番号
   static final int BUFSIZE = 1792;  //バッファサイズ
   public static void main(String[] args)
   {
      int len;  //データ長
      int i;  //キーインバッファクリアインデックス
      byte[] wbuf = new byte[BUFSIZE];  //ワークバッファ
      MulticastSocket sock = null;  //マルチキャストソケット
      InetAddress ipadr;  //マルチキャストIPアドレス
      //new begin
      FileOutputStream out = null;		//file
      BufferedOutputStream bout = null;	//bin file
      DatagramPacket getPacket = null;	//get packet
      //new end
      if(args.length != 1){
         throw new IllegalArgumentException(
         "usage: >java multicastClient ＜IP_Address＞");
      }
      try
      {
         //マルチキャストソケット設定
         sock = new MulticastSocket(MULTI_PORT);
         //IPアドレス設定
         ipadr = InetAddress.getByName(args[0]);
         sock.joinGroup(ipadr);
         byte[] buf = new byte[BUFSIZE];  //受信バッファ
         while(true){
            //データグラムパケット
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            sock.receive(packet);  //データ受信
            String recvdata = new String(packet.getData());
            recvdata = recvdata.trim();
            wbuf = recvdata.getBytes();  //文字列→バイト配列変換
            i = 0;
            len = wbuf.length;
            buf = new byte[BUFSIZE];
            while(wbuf[i] != 0x0d){  //途中のCR検出
               buf[i] = wbuf[i];  //データコピー
               i++;
               if(i >= len){
                  break;
               }
            }
            recvdata = new String(buf,0,i);  //バイト配列→文字列変換
            if("QUIT".toString().equalsIgnoreCase(recvdata)){
               break;
            }
            System.out.println("Get:"+recvdata);  //画面出力
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

