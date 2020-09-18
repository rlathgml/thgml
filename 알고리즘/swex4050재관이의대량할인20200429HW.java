import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class swex4050재관이의대량할인20200429HW {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			Integer clothes[] = new Integer[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				clothes[i] = Integer.parseInt(st.nextToken());
			}
			Arrays.sort(clothes, Comparator.reverseOrder());
			
			int sum = 0;
			for (int i = 0; i < clothes.length; i++) {
				if(i%3 == 2) continue;
				sum += clothes[i];
			}
			sb.append("#"+ t + " " + sum + "\n");
		}
		System.out.println(sb.toString());
	}

}
