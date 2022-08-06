import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int n, m, r; // n: 낙하위치 개수, m: 수색범위, r: 경로 개수
    static int[] weapons; // 낙하위치에 있는 아이템 갯수
    static int[][] floyd; // 모든 낙하 위치에서 다른 모든 낙하 위치까지의 최단 경로

    public static void main(String[] args) throws IOException {
        input(); // 입력값 받기
        floydWashall(); // 플로이드 와샬
        bw.write(String.valueOf(findMaxItems())); // 얻을 수 있는 아이템의 최대 갯수 출력
        bw.flush();
    }

    // 플로이드 와샬
    public static void floydWashall() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (floyd[i][j] > floyd[i][k] + floyd[k][j]) {
                        floyd[i][j] = floyd[i][k] + floyd[k][j];
                    }
                }
            }
        }
    }

    // 각 낙하위치에서 수색 범위 안으로 도착할 수 있는 낙하위치의 아이템들을 비교하여 최댓값 탐색
    public static int findMaxItems() {
        int maxItems = 0;
        for (int i = 1; i <= n; i++) {
            int items = 0;
            for (int j = 1; j <= n; j++) {
                if (floyd[i][j] <= m) {
                    items += weapons[j];
                }
            }
            if (maxItems < items) {
                maxItems = items;
            }
        }

        return maxItems;
    }

    // 입력값 받기
    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        weapons = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            weapons[i] = Integer.parseInt(st.nextToken());
        }

        floyd = new int[n + 1][n + 1];
        for (int[] f : floyd) {
            Arrays.fill(f, 2000);
        }

        for (int i = 1; i <= n; i++) {
            floyd[i][i] = 0;
        }

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            floyd[a][b] = Math.min(floyd[a][b], c);
            floyd[b][a] = Math.min(floyd[b][a], c);
        }
    }
}
