import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class swex4366정식이의은행업무20200501 {
	static String bi, tri;
	static int ans;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int t = 1; t <= T; t++) {
			ans = 0;
			bi = br.readLine(); //2진수
			tri = br.readLine(); //3진수

			StringBuilder tmp = null;
			ArrayList<Integer> biList = new ArrayList<>();
			for (int i = 0; i < bi.length(); i++) {	// 가능한 2진수 추측
				tmp = new StringBuilder(bi);
				if(tmp.charAt(i) == '1') {
					tmp.setCharAt(i, '0');
				} else {
					tmp.setCharAt(i, '1');
				}
				biList.add(Integer.parseInt(tmp.toString(),2));
			}

			for (int i = 0; i < tri.length(); i++) {	// 가능한 3진수 추측
				tmp = new StringBuilder(tri);
				if(tmp.charAt(i) == '0') {
					tmp.setCharAt(i, '1');
					int n = Integer.parseInt(tmp.toString(),3);
					if(biList.contains(n)) {
						ans = n;
						break;
					}
					tmp.setCharAt(i, '2');
					n = Integer.parseInt(tmp.toString(),3);
					if(biList.contains(n)) {
						ans = n;
						break;
					}
				} else if(tmp.charAt(i) == '1') {
					tmp.setCharAt(i, '0');
					int n = Integer.parseInt(tmp.toString(),3);
					if(biList.contains(n)) {
						ans = n;
						break;
					}
					tmp.setCharAt(i, '2');
					n = Integer.parseInt(tmp.toString(),3);
					if(biList.contains(n)) {
						ans = n;
						break;
					}
				} else {
					tmp.setCharAt(i, '1');
					int n = Integer.parseInt(tmp.toString(),3);
					if(biList.contains(n)) {
						ans = n;
						break;
					}
					tmp.setCharAt(i, '0');
					n = Integer.parseInt(tmp.toString(),3);
					if(biList.contains(n)) {
						ans = n;
						break;
					}
				}
			}

			sb.append("#"+ t + " " + ans + "\n");
		}
		System.out.println(sb.toString());
	}
}
