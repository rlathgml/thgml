import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashSet;

public class boj1062가르침_20200521HW {
	public static char charSet[]; //후보군 배열
	public static boolean visit[]; //백트래킹 방문 여부 체크
	public static int n, k, strSize, max = 0, candidateSize;
	public static String str[]; //단어저장소
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		String line[] = in.readLine().split(" "), word;
		int i, j, w = 0, wordSize, preCount = 0;
		n = Integer.parseInt(line[0]); 
		k = Integer.parseInt(line[1]);
		str = new String[n]; 
		HashSet<Character> candidate = new HashSet<>();
		
		for(i=0;i<n;i++){
			word = in.readLine();
			word = word.replaceAll("[antic]", "");
			wordSize = word.length();
			if(wordSize==0) preCount++;
			else{
				str[w++] = word;
				for(j=0;j<wordSize;j++) candidate.add(word.charAt(j));
			}
		}
		if(k<5) preCount = max = 0;
		else{
			k-=5; 
			strSize = w;
			candidateSize = candidate.size();
			charSet = new char[candidateSize];
			visit = new boolean[candidateSize];
			i = 0;
			for(char ch : candidate) charSet[i++] = ch;
			if(candidateSize<k) k = candidateSize;
			dfs(new HashSet<>(), 0, 0);
		}
		out.write(String.valueOf(preCount+max));
		out.close();
		in.close();
	}
	
	private static void dfs(HashSet<Character> picked, int idx, int pick){
		int i, j, count = 0, wordSize;
		if(pick==k){
			boolean impossible;
			for(i=0;i<strSize;i++){
				impossible = false;
				wordSize = str[i].length();
				for(j=0;j<wordSize;j++)
					if(!picked.contains(str[i].charAt(j))){
						impossible = true;
						break;
					}
				if(!impossible) count++;
			}
			if(max < count) max = count;
			return;
		}
		for(i=idx;i<candidateSize;i++)
			if(!visit[i]){
				visit[i] = true;
                picked.add(charSet[i]);			
               
                dfs(picked, i, pick+1);
				
                visit[i] = false;
				picked.remove(charSet[i]);
			}
	}
}