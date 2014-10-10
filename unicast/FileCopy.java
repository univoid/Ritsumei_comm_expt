/**
 *
 * FileCopy.java
 *
 * �p�@��
 * >java FileCopy ��Data1�� ��Data2��
 */
import java.io.*;

class FileCopy {

   public static void main(String argv[]){
      try{
         InputStream in = new FileInputStream(argv[0]);  //���̓t�@�C�����J��
         //BufferedInputStream�I�u�W�F�N�g�̐���
         BufferedInputStream bin = new BufferedInputStream(in);
         OutputStream out = new FileOutputStream(argv[1]);  //�o�̓t�@�C�����J��
         //BufferedOutputStream�I�u�W�F�N�g�̐���
         BufferedOutputStream bout = new BufferedOutputStream(out);
         byte buf[] = new byte[1024];
         int c;
         //�ǂݍ��݃f�[�^���Ȃ��Ȃ�܂œǂݍ���
         while((c = bin.read(buf, 0, buf.length)) != -1){
            bout.write(buf, 0, c);  //�f�[�^�̏������ݏ���
         }
         System.out.println("\""+argv[0]+"\" was converted into \""+argv[1]+"\"");
         bin.close();  //���̓t�@�C�������
         bout.close();  //�o�̓t�@�C�������
      }catch(Exception e){}
   }
}
