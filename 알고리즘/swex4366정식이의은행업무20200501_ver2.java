import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class swex4366정식이의은행업무20200501 {
	static String bi, tri;
	static int ans;
	static ArrayList<Integer> biList;
	static StringBuilder tmp;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int t = 1; t <= T; t++) {
			ans = 0;
			bi = br.readLine(); //2진수
			tri = br.readLine(); //3진수

			tmp = null;
			biList = new ArrayList<>();
			int num = Integer.parseInt(bi, 2);
			for (int i = 0; i < bi.length(); i++) {	// 가능한 2진수 추측
				biList.add(num ^ (1 << i));
			}

			for (int i = 0; i < tri.length(); i++) {	// 가능한 3진수 추측
				tmp = new StringBuilder(tri);
				if(tmp.charAt(i) == '0') {
					tri(i, tmp, '1', '2');
				} else if(tmp.charAt(i) == '1') {
					tri(i, tmp, '0', '2');
				} else {
					tri(i, tmp, '1', '0');
				}
			}

			sb.append("#"+ t + " " + ans + "\n");
		}
		System.out.println(sb.toString());
	}
	static void tri(int i, StringBuilder tmp, char a, char b) {
		tmp.setCharAt(i, a);
		int n = Integer.parseInt(tmp.toString(),3);
		if(biList.contains(n)) {
			ans = n;
			return;
		}
		tmp.setCharAt(i, b);
		n = Integer.parseInt(tmp.toString(),3);
		if(biList.contains(n)) {
			ans = n;
			return;
		}
	}
}
