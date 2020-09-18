import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class bj2636치즈 {
	private static int R, C, day, pre;
    private static int[][] board;
    private static boolean[][] visited;
    private static Queue<Point> meltingQueue = new LinkedList<>();
    private static int[] dx = {0, -1, 0, 1};
    private static int[] dy = {-1, 0, 1, 0};

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        R = sc.nextInt();
        C = sc.nextInt();

        board = new int[R + 1][C + 1];

        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                board[i][j] = sc.nextInt();
            }
        }

        while (true) {
            visited = new boolean[R + 1][C + 1];
            
            // 바깥 공기를 -1로 바꾼다.
            checkOutside();

            // 치즈의 가장자리 구역을 찾고, meltingQueue에 저장
            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    if (board[i][j] == 1 && isEdge(i, j)) {
                        bfs(i, j);
                    }
                }
            }

            // 녹을 치즈가 더이상 없는 경우 종료
            if (meltingQueue.isEmpty()) break;

            // 모든 치즈가 녹기 한 시간 전 남은 치즈적 면 == 마지막으로 녹아야할 치즈 면
            pre = meltingQueue.size();

			// meltingQueue에 있는 치즈 녹이기
            while (!meltingQueue.isEmpty()) {
                Point point = meltingQueue.poll();
                board[point.x][point.y] = -1;
            }

            // 날짜++
            day++;
        }

        System.out.println(day + " " + pre);
    }

    private static void bfs(int x, int y) {
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(x, y));
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (isEdge(point.x, point.y)) {
                board[point.x][point.y] = 2;
                meltingQueue.add(new Point(point.x, point.y));
            }

            for (int i = 0; i < 4; i++) {
                int nx = point.x + dx[i];
                int ny = point.y + dy[i];

                if (isIn(nx, ny) && board[nx][ny] == 1 && !visited[nx][ny]) {
                    queue.add(new Point(nx, ny));
                    visited[nx][ny] = true;
                }
            }
        }
    }

    private static boolean isEdge(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (board[nx][ny] == -1) {
                return true;
            }
        }

        return false;
    }

    private static void checkOutside() {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visitedAir = new boolean[R + 1][C + 1];

        queue.add(new Point(1, 1));
        board[1][1] = -1;
        visitedAir[1][1] = true;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = point.x + dx[i];
                int ny = point.y + dy[i];

                if (isIn(nx, ny) && !visitedAir[nx][ny] && board[nx][ny] <= 0) {
                    board[nx][ny] = -1;
                    visitedAir[nx][ny] = true;
                    queue.add(new Point(nx, ny));
                }
            }
        }
    }

    private static boolean isIn(int x, int y) {
        return x > 0 && x <= R && y > 0 && y <= C;
    }

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
