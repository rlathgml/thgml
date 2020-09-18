import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class swex5607Professional조합 {

	private static final int MOD = 1234567891;
	static int n, r;
	static long arr[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	n = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken());
            arr = new long[n + 1];
            arr[0] = 1;
            for (int i = 1; i <= n; i++) {
            	arr[i] = (arr[i - 1] * i) % MOD;
            }

            long bottom = (arr[r] * arr[n - r]) % MOD;
            long bottom2 = fermat(bottom, MOD - 2);

            bw.append("#" + t + " " + (arr[n] * bottom2) % MOD + "\n");
        }
        
        bw.flush();
        
        bw.close();
        br.close();
    }
    
    private static long fermat(long n, int x) {
        if (x == 0) return 1;
        long tmp = fermat(n, x / 2);
        long ret = (tmp * tmp) % MOD;
        if (x % 2 == 0) return ret;
        else return (ret * n) % MOD;
    }
}
