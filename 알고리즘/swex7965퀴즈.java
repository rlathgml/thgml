import java.util.Scanner;
 
public class swex7965ДыБо {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int TC = sc.nextInt();
        for(int tc = 1; tc <= TC; tc++) {
            int N = sc.nextInt();
             
            int sum = 0;
            for(int i = 1; i <= N; i++) {
                sum += pow(i,i);
                if(sum > 1000000007) sum %= 1000000007;
            }
            System.out.println("#" + tc + " " + sum);
        }
    }
    static long pow(int base, int exp) {
        if(exp == 0) {return 1;}
        if(exp == 1) {return base;}
         
        long num = pow(base, exp/2);
         
        num=(num*num)%1000000007;
        if(exp%2==1) {num=(num*base)%1000000007;}     
        return num;
    }
}