import java.util.Scanner;
import java.util.Stack;

//정올 1809 탑
public class 정올1809탑 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int[] tower = new int[n+1];
		int[] res = new int[tower.length];
		
		for(int i = 1; i < tower.length; i++) {
			tower[i] = sc.nextInt();
		}
		
		Stack<Integer> s = new Stack<Integer>();
		
//		s.push(1); //첫번째 탑 높이 무조건 넣기
		int pre = 0;	//이전에 가장 높은 타워 번호
		int now = 0;	//현재 가장 높은 타워 번호
		int idx = 1;
		for(int i = 1; i < tower.length; i++) {
			if(s.isEmpty() || tower[s.peek()] <= tower[i] ) {//top 에 있는 타워 높이보다 지금 높이가 더 높으면 push
				s.push(i);
			}else {
				now = s.peek();	//지금 제일 높은 타워 번호 저장
//				while(!s.isEmpty()) {
				while(tower[s.peek()] > tower[i]) {
//					if(tower[s.peek()] <= tower[i])
//						break;
					if(s.isEmpty()) break;
					s.pop();
					res[idx++] = pre; 
				}
				pre = now;
				s.push(i);
			}
		}
		
		while(!s.isEmpty()) {
			s.pop();
			res[idx++] = pre;
		}
		
		for(int i = 1; i < res.length; i++) {
			System.out.print(res[i] + " ");
		}
		System.out.println();
	}
}