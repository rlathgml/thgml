package unionset;

import java.util.Scanner;

public class swex3289서로소집합 {
	static int[] set;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n, m, order, a, b;
			n = sc.nextInt();
			m = sc.nextInt();
			set = new int[n+1];

			make();

			for(int i = 0; i < m; i++) {
				order = sc.nextInt();
				a = sc.nextInt();
				b = sc.nextInt();

				if(order == 0) {
					union(a,b);
				}else if(order == 1) {
					if(find(a) == find(b))System.out.println("YES");
					else System.out.println("NO");
				}
			}
		}
	static void make() {
		for(int i = 0; i < set.length; i++) {
			set[i] = i;
		}
	}
	static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if(a == b) return;
		set[b] = a;
	}
	static int find(int a) {
		if(a != set[a]) {	//내 부모가 다르면
			set[a] = find(set[a]);	//부모의 부모를 찾아서 내 부모를 부모의 부모로 바꿈
			return set[a];
		}
		return a;
	}
}
