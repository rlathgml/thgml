import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Point{
	int x,y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class bj2468안전영역_20200514HW {

	static int ans = 0;
	static int N;
	static int[] dx = {0,-1,0,1};
	static int[] dy = {-1,0,1,0};
	static int[][] map;
	static boolean[][] visited;
	static Queue<Point> queue = new LinkedList<>();
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine().trim());
		
		map = new int[N][N];
		int max = Integer.MIN_VALUE;
		
		StringTokenizer st = null;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int a = map[i][j] = Integer.parseInt(st.nextToken());
				if(a > max) max = a;
			}
		}
		
		for (int m = 0; m < max; m++) {
			int result = 0;
			visited = new boolean[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(!visited[i][j] && map[i][j] > m) {
						bfs(m, new Point(i,j));
						result++;
					}
				}
			}
			if(ans < result) {
				ans = result;
			}
		}
		System.out.println(ans);
	}

	static void bfs(int i, Point point) {
		queue.offer(point);
		visited[point.x][point.y] = true;
		
		while(!queue.isEmpty()) {
			Point p = queue.poll();
			
			for (int j = 0; j < 4; j++) {
				int nx = p.x + dx[j];
				int ny = p.y + dy[j];
				
				//범위밖||방문했음||강수량이하 -> 넘어감
				if(!isIn(nx,ny) || visited[nx][ny] || map[nx][ny] <= i) continue;	
				
				visited[nx][ny] = true;
				queue.offer(new Point(nx,ny));
			}
		}
	}

	static boolean isIn(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}