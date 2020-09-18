import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Bj7576토마토_modified {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int M, N;
	static int[][] tomato;
	static Queue<Integer> qx = new LinkedList<Integer>();
	static Queue<Integer> qy = new LinkedList<Integer>();
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());	//가로 열
		N = Integer.parseInt(st.nextToken());	//세로 행
		tomato = new int[N][M];

		int cnt = 0;
		for(int i = 0; i < N; i++) {	// 토마토 입력
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				int a = Integer.parseInt(st.nextToken());
				tomato[i][j] = a;
				if(a == 0) {
					cnt++;
				}
				if(a == 1) {	// 초기에 익은 토마토 위치 기억
					qx.offer(i);
					qy.offer(j);
				}
			}
		}
		if(cnt == 0) {	//모든 토마토가 익어있는 상태 => 입력할 때 0이 없음
			bw.append('0');
			bw.flush();
			return;
		}else {
			int day = 1;
			while(!qx.isEmpty()) {
				bfs(qx.poll(), qy.poll());
			}
		}
		
		int max = Integer.MIN_VALUE;
		Loop:
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(tomato[i][j] == 0) {
					bw.append("-1");
					bw.flush();
					return;
				}
				max = Math.max(max, tomato[i][j]);
			}
		}
		bw.append(max - 1 + "");
		
		bw.flush();
		bw.close();
		br.close();
	}

	static void bfs(int x, int y) {
		int nx = 0, ny = 0;
		for(int i = 0; i < 4; i++) {
			nx = x + dx[i];
			ny = y + dy[i];

			if(nx < 0 || nx >= N || ny < 0 || ny >= M || tomato[nx][ny] != 0) continue;
			tomato[nx][ny] = tomato[x][y]+1;
			qx.offer(nx);	qy.offer(ny);
		}
	}
}