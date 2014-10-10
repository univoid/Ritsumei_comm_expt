/**
 *
 * FileCopy.java
 *
 * 用法例
 * >java FileCopy ＜Data1＞ ＜Data2＞
 */
import java.io.*;

class FileCopy {

   public static void main(String argv[]){
      try{
         InputStream in = new FileInputStream(argv[0]);  //入力ファイルを開く
         //BufferedInputStreamオブジェクトの生成
         BufferedInputStream bin = new BufferedInputStream(in);
         OutputStream out = new FileOutputStream(argv[1]);  //出力ファイルを開く
         //BufferedOutputStreamオブジェクトの生成
         BufferedOutputStream bout = new BufferedOutputStream(out);
         byte buf[] = new byte[1024];
         int c;
         //読み込みデータがなくなるまで読み込み
         while((c = bin.read(buf, 0, buf.length)) != -1){
            bout.write(buf, 0, c);  //データの書き込み処理
         }
         System.out.println("\""+argv[0]+"\" was converted into \""+argv[1]+"\"");
         bin.close();  //入力ファイルを閉じる
         bout.close();  //出力ファイルを閉じる
      }catch(Exception e){}
   }
}
