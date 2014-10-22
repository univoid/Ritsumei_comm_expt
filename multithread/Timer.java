/**
 *
 * Timer.java
 *
 * �p�@��
 * >java Timer
 */

import java.io.*;

public class Timer {
    public static void main(String[] args) throws IOException {
        long start, end;
        start = System.currentTimeMillis();  //�J�n����
        System.out.println("please input some key");
        System.in.read();  //�L�[���
        end = System.currentTimeMillis();  //�I������
        //�v�Z���Ԃ�\���istart, end�̓~���b�P�ʁj
        System.out.println("time: " + ((end - start) / 1000.0) + "sec");
    }
}
