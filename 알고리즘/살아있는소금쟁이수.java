package com.ssafy.algo;

import java.io.FileInputStream;
import java.util.Scanner;

public class 살아있는소금쟁이수 {
	static long Answer;
	static int N, S;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("salt.txt"));
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();// 테스트 케이스 수

		for (int t = 1; t <= T; t++) {
			N = sc.nextInt(); // 연못의 크기 NxN
			int[][] lake = new int[N][N];

			S = sc.nextInt();// 소금쟁이(Strider)수
			int[][] strider = new int[S][3];
			for (int k = 0; k < S; k++) {
				strider[k][0] = sc.nextInt();// 행위치
				strider[k][1] = sc.nextInt();// 열위치
				strider[k][2] = sc.nextInt();// 방향(1:하,2:우)
			}

			Answer = S;
			int x,y,d;
			for(int i = 1; i <= S; i++) {
				x = strider[i-1][0];
				y = strider[i-1][1];
				d = strider[i-1][2];

				if(Answer <= 0) break;


				//for(int j = 0; j < 3; j++) {//점프
				if(d == 1) {	//상
					x -= 6;
					if(x < 0) {
						Answer--;
						continue;
					}
					if(lake[x][y] == 0) {
						lake[x][y] = i;
					}else {
						Answer--;
						continue;
					}
				}else if(d == 2){	//하
					x += 6;
					if(x >= N) {
						Answer--;
						continue;
					}

					if(lake[x][y] == 0) {
						lake[x][y] = i;
					}else {
						Answer--;
						continue;
					}
				}else if(d == 3){	//좌
					y -= 6;
					if(y < 0) {
						Answer--;
						continue;
					}

					if(lake[x][y] == 0) {
						lake[x][y] = i;
					}else {
						Answer--;
						continue;
					}
				}
				else{	//우
					y += 6;
					if(y >= N) {
						Answer--;
						continue;
					}

					if(lake[x][y] == 0) {
						lake[x][y] = i;
					}else {
						Answer--;
						continue;
					}
				} 
				//}
			}

			System.out.println("#" + t + " " + Answer);
		}
	}
}