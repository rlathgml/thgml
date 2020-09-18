import java.io.IOException;
import java.util.Scanner;

public class bj1786KMP {
	//모든 경우를 다 비교하지 않아도 부분 문자열을 찾을 수 있는 알고리즘
	//접두사와 접미사 개념을 활용해서, '반복되는 연산을 최대한 줄이도록'
	//지금까지 ㅓㄱㅁ사한 문자열의 접두사와 접미사가 일치하는 최대길이만큼 겹침을 이용
	
	//파이테이블 : 각 길이별로 접두사==접미사의 최대길이가 저장된 배열
	static int[] getPi(String p) {
		int pi[] = new int[p.length()];
		int j = 0;
		for (int i = 1; i < p.length(); i++) {
			while(j > 0 && p.charAt(i) != p.charAt(j))
				j = pi[j-1];
			
			//맞는경우
			if(p.charAt(i) == p.charAt(j)) {
				//i길이 문자열의 공통길이는 j의 위치 + 1
				pi[i] = ++j;
			}
//			//안맞는경우
//			else {
//				//맞는게 나올때까지 하나 전칸에서의 공통부분 위치로 이동
//				
//			}
		}
		return pi;
	}
	
	static void KMP(String t, String p) {
		int[] pi = getPi(p);
		int j=0;
		int cnt = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t.length(); i++) {
			while(j > 0 && t.charAt(i) != p.charAt(j))
				j = pi[j-1];
			//맞는경우
			if(t.charAt(i) == p.charAt(j)) {
				if(j == p.length() -1) {
					sb.append(i - p.length() + 1 + 1 +" ");
					cnt++;
					j = pi[j];
				}
				//맞았지만 패턴이 끝나지 않았다면 j 하나 증가
				else j++;
			}
		}
		System.out.println(cnt);
		System.out.println(sb.toString());
	}
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		KMP(sc.nextLine(), sc.nextLine());
	}
}

