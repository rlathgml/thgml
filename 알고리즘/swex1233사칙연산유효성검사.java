import java.util.Arrays;
import java.util.Scanner;
 
public class swex1233사칙연산유효성검사 {
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
 
        int N, ans;
        char arr;
        String[] str;
 
        LOOP :
            for(int tc = 1; tc <= 10; tc++) {
                N = Integer.parseInt(sc.nextLine());
                ans = 1;
                if(N % 2 == 0) {
                    ans = 0;
                }
                for(int i = 1; i <= N/2; i++) {
                    str = sc.nextLine().split(" ");
                    arr = str[1].charAt(0);
                    if(arr != '+' && arr != '*' && arr != '-' && arr != '/') {
                        ans = 0;
                    }
                }
                for(int i = N/2 +1; i <= N; i++) {
                    str = sc.nextLine().split(" ");
                    arr = str[1].charAt(0);
                    if( !Character.isDigit(arr) ) {
                        ans = 0;
                    }
                }
                System.out.println(String.format("#%d %d", tc, ans));
            }
    }
}