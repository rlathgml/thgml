import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class bj2252줄세우기_20200513HW_위상정렬 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] indegree = new int[N+1];
		ArrayList<Integer>[] link = new ArrayList[N+1];
		for (int i = 0; i < N+1; i++) {
			link[i] = new ArrayList<Integer>();
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			indegree[b]++;
			link[a].add(b);
		}
		
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i < N+1; i++) {
			if(indegree[i] == 0) queue.offer(i);
		}
		
		while(!queue.isEmpty()) {
			int student = queue.poll();
			sb.append(student).append(" ");
			
			int size = link[student].size();
			for (int j = 0; j < size; j++) {
				int tmp = link[student].get(j);
				--indegree[tmp];
				if(indegree[tmp] == 0) {
					queue.offer(tmp);
				}
			}
		}
		System.out.println(sb.toString());
	}
}
