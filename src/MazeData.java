import java.io.*;
import java.util.Scanner;

/**
 * 迷宫数据模型
 * @Author: renxiaoya
 * @Date: 2018/9/26 17 48
 */
public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    // N行，M列
    private int N,M;
    public char[][] maze;

    private int entranceX,entranceY;
    private int exitX,exitY;

    public boolean[][] visited;
    public boolean[][] path;

    /**
     * 随机生成的迷宫
     * @param N
     * @param M
     */
    public MazeData(int N, int M){
        //N，M必须是奇数
        if (N % 2 == 0 || M % 2 == 0){
            throw new IllegalArgumentException("n,m must be odd number");
        }

        this.N = N;
        this.M = M;

        maze = new char[N][M];
        visited = new boolean[N][M];
        path = new boolean[N][M];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                if (i % 2 == 1 && j % 2 == 1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;
            }
        }

        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;

        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;
    }

    /**
     * 根据文件读取迷宫
     * @param filename
     */
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
            visited = new boolean[N][M];
            path = new boolean[N][M];
            //读取后续的行
            for (int i = 0; i < N; i ++){
                String line = scanner.nextLine();

                //保证每行都有M个字符
                if (line.length() != M){
                    throw new IllegalArgumentException("Maze file " + filename + " has illegal format! ");
                }

                for (int j = 0; j < M; j++){
                    maze[i][j] = line.charAt(j);
                    visited[i][j] = false;
                    path[i][j] = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null){
                scanner.close();
            }
        }
        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;
    }

    public int N(){
        return N;
    }

    public int M(){
        return M;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
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
