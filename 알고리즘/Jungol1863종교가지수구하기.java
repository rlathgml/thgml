import java.util.Scanner;

public class Jungol1863 {
	static int[] rel;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int m = sc.nextInt();
		rel = new int[n+1];
		make();
		int a,b;
		for(int i = 0; i < m; i++) {
			a = sc.nextInt();
			b = sc.nextInt();
			union(a,b);
		}
		int num = 0;
		for(int i = 1; i <= n; i++) {
			if(rel[i] == i) num++;
		}
		System.out.println(num);
	}	
	static void make() {
		for(int i = 1; i < rel.length; i++ ){
			rel[i] = i;
		}
	}
	static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if(a == b) return;
		rel[b] = a;
	}
	static int find(int a) {
		if(a == rel[a]) return a;
		return rel[a] = find(rel[a]);
	}
}
/*
 * 최빈수 구하기 처럼
 * cnt[rel[i]] = true;
 * 하고 true 수 세서 하면 틀림
 * 
 * cnt[find(rel[i])] 했더니 StackOverflow 오류 남
 */