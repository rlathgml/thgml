import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class bj6987_월드컵_20200506 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int win = 0, lose = 0, draw=0, tmp = 0, total = 0;
			for (int j = 0; j < 18; j++) {
				if(j%3 == 0) win += Integer.parseInt(st.nextToken());
				if(j%3 == 1) {
					tmp = Integer.parseInt(st.nextToken());
					if(tmp == 0) continue;
					
					if(draw == 0) {
						draw += tmp;
					} else if(tmp <= draw) {
						draw -= tmp;
					} else if(tmp > draw) {
						draw += tmp;
					}
				}
				if(j%3 == 2) lose += Integer.parseInt(st.nextToken());
				total += win + tmp + lose;
			}
			if(win == lose && draw == 0) sb.append("1 ");
			else if(total != 30) sb.append("0 ");
		}
		System.out.println(sb.toString());
	}
}
