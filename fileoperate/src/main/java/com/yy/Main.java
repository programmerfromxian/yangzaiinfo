package com.yy;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

/**
 * description
 *
 * @author yangzai
 * @date 2021/04/13
 */
public class Main {

    private static long initLineNum = 0;

    private static long initFileSize = 0;

    private static String filepath = "";

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder("param is ");
        if (null != args && args.length > 0) {
            for (String arg : args) {
                sb.append(arg + ", ");
            }
        }
        System.out.println(sb.toString());
        filepath = args[0];
        System.out.println("tail file is " + filepath);
        initLineNum = Files.lines(Paths.get(filepath)).count();
        File file = new File(filepath);
        initFileSize = file.length();
        System.out.println("file " + args[0] + " init line number is " + initLineNum + " , size is " + initFileSize + ".");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                long filesize = new File(filepath).length();
                try {
                    long newLineNum = Files.lines(Paths.get(filepath)).count();
                    if (filesize == initFileSize) {
                        return;
                    }
                    readIncrease(initFileSize, filesize, newLineNum);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 10, 10);
    }

    private static void readIncrease(long originalSize, long newSize, long newLineNum) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(new File(filepath), "rw");
            randomAccessFile.seek(originalSize);
            while (randomAccessFile.getChannel().position() < newSize) {
                System.out.println(randomAccessFile.readLine());
            }
            initFileSize = newSize;
            initLineNum = newLineNum;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
