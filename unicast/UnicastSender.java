/**

 *
 * 用法例
 * >java UnicastSender 192.168.1.1 data.txt
 */
import java.net.*;
import java.io.*;

public class UnicastSender{
   static final int echo_PORT = 7;  //エコーポート番号
   static final int BUFSIZE = 1792;  //バッファサイズ

   public static void main(String[] args) throws IOException{
      int port = echo_PORT;  //ポート番号
      //byte[] buf = new byte[BUFSIZE];  //送受信バッファ
      String fileName;  //file name
      DatagramSocket sock = null;  //データグラムソケット宣言，初期化
      if(args.length != 1){
         throw new IllegalArgumentException("usage: >java  UnicastSender＜IP_Address＞");
      }
      while(true){
         try{
            InetAddress adr = InetAddress.getByName(args[0]);  //サーバIPアドレス
            //System.out.print("cli>");  //プロンプト出力
            fileName = args[1];  //キー入力

	        //input begin
	        InputStream in = new FileInputStream(fileName);  //入力ファイルを開く
            //BufferedInputStreamオブジェクトの生成
            BufferedInputStream bin = new BufferedInputStream(in);
            byte buf[] = new byte[BUFSIZE];
            int c;
	        sock =new DatagramSocket();
            //読み込みデータがなくなるまで読み込み
            while((c = bin.read(buf)) != -1){
               DatagramPacket packet = new DatagramPacket(buf,c,adr,port);
               sock.send(packet);  //入力データ送信
               sock.receive(packet);  //エコーデータ受信
            }
	        //input end
	        //
            //sock = new DatagramSocket();
            //DatagramPacket packet = new DatagramPacket(buf,buf.length,adr,port);
            //sock.send(packet);  //入力データ送信
            //buf = new byte[BUFSIZE];
            //packet = new DatagramPacket(buf,buf.length);
            //sock.receive(packet);  //エコーデータ受信
            //String data = new String(packet.getData(),0,packet.getLength());
            //System.out.println(data);  //画面出力
	        bin.close();  //入力ファイルを閉じる
            sock.close();
         }catch(SocketException e){
            e.printStackTrace();
         }
      }
   }
}
