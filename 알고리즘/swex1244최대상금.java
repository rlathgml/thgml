import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
 
public class swex1244최대상금 {
    static char[] number;
    static int trade;
    static int ans;
    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine());
        int tc = Integer.parseInt(st.nextToken());
        for(int t = 1; t <= tc; ++t) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            trade = Integer.parseInt(st.nextToken());
            number = s.toCharArray();
            ans = 0;
            dfs(0,0);
            bw.write("#" + t + " " + ans + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
 
    private static void dfs(int cnt, int now)
    {
        if (cnt == trade)
        {
            int stoi = Integer.parseInt(String.valueOf(number));
            if(ans < stoi)
                ans = stoi;
            return;
        }
        for (int i = now; i < number.length; ++i)
        {
            for (int j = i; j < number.length; ++j)
            {
                if (i == j) continue;
                if (number[i] <= number[j])
                {
                    swap(i, j);
                    dfs(cnt + 1, i);
                    swap(i, j);
                }
            }
        }
    }
     
    private static void swap(int a, int b) {
        char temp = number[b];
        number[b] = number[a];
        number[a] = temp;
    }
}