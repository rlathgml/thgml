import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class swex9659다항식계산 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N, M, ti[], ai[], bi[];	//다항식수 , M개의 수
	static long[]   ans;
	public static void main(String[] args) throws NumberFormatException, IOException {
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			bw.append("#" + t + " ");
			
			N = Integer.parseInt(br.readLine());
			
			ti = new int[N+1];
			ai = new int[N+1];
			bi = new int[N+1];
			for (int i = 2; i <= N; i++) {
				st = new StringTokenizer(br.readLine());
				ti[i] = Integer.parseInt(st.nextToken());
				ai[i] = Integer.parseInt(st.nextToken());
				bi[i] = Integer.parseInt(st.nextToken());
			}
			M = Integer.parseInt(br.readLine());
			
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < M; i++) {
				ans = new long[N+1];
				ans[0] = 1; 
				ans[1] = Integer.parseInt(st.nextToken());
				for (int j = 2; j <= N; j++) {
					switch(ti[j]) {
					case 1:
						ans[j] = ans[ai[j]] + ans[bi[j]];
						break;
					case 2:
						ans[j] = ai[j] * ans[bi[j]];
						break;
					case 3:
						ans[j] = ans[ai[j]] * ans[bi[j]];
						break;
					}
					if (ans[j] > 998244353)
						ans[j] %= 998244353;
				}
				bw.append(ans[N] + " ");
			}
			bw.append("\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
}