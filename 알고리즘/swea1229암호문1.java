import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
 
//암호문1
public class swea1229암호문1{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T;
        T=10;
         
        int length , order;
        LinkedList<Integer> list;
         
        for(int test_case = 1; test_case <= T; test_case++)
        {
            length = sc.nextInt();  //암호문길이
             
            list = new LinkedList<Integer>();
            for(int i = 0; i < length; i++) {    //원본암호문
                list.add(sc.nextInt());
            }
             
            order = sc.nextInt();   //명령어수
 
            char ch;
            int x, y;
            int z = 0;
            for(int i = 0; i < order; i++) {
                ch =  sc.next().charAt(0);
                x = sc.nextInt();
                y = sc.nextInt();
                if(ch == 'I') {
                    for(int j = 0; j < y; j++) { 
                        list.add(x++,sc.nextInt());
                    }
                } else if(ch == 'D') {
                    for(int j = 0; j < y; j++) {
                        list.remove(x);
                    }
                }
            }
             
            int cnt = 0;
            System.out.printf("#%d ",test_case);
            for(int ans : list) {
                if(cnt == 10) break;
                System.out.print(ans + " ");
                cnt++;
            }
            System.out.println();
        }
    }
}