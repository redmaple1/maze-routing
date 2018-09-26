import java.io.*;
import java.util.Scanner;

/**
 * 迷宫数据模型
 * @Author: renxiaoya
 * @Date: 2018/9/26 17 48
 */
public class MazeData {

    // N行，M列
    private int N,M;
    private char[][] maze;

    public MazeData(String filename){
        if (filename == null){
            throw new IllegalArgumentException("Filename can not be null");
        }
        Scanner scanner = null;
        try {
            File file = new File(filename);
            if (!file.exists()){
                throw new IllegalArgumentException("File " + filename + " doesn't exist ");
            }

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis),"UTF-8");

            //读取第一行
            String nmLine = scanner.nextLine();
            String[] nm = nmLine.trim().split("\\s+");

            N = Integer.parseInt(nm[0]);
            M = Integer.parseInt(nm[1]);

            maze = new char[N][M];
            //读取后续的行
            for (int i = 0; i < N; i ++){
                String line = scanner.nextLine();

                //保证每行都有M个字符
                if (line.length() != M){
                    throw new IllegalArgumentException("Maze file " + filename + " has illegal format! ");
                }

                for (int j = 0; j < M; j++){
                    maze[i][j] = line.charAt(j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null){
                scanner.close();
            }
        }
    }

    public int N(){
        return N;
    }

    public int M(){
        return M;
    }

    public char getMaze(int i, int j){
        if (!inArea(i,j)){
            throw new IllegalArgumentException("i or j is out of index in getMaze!");
        }
        return maze[i][j];
    }

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print(){
        System.out.println(N + " " + M);
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}