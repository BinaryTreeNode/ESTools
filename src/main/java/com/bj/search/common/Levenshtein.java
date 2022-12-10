package com.bj.search.common;


import lombok.extern.slf4j.Slf4j;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */

@Slf4j
public class Levenshtein {

    private static int compare(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {
            // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) {
            // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static int compare(Long[] str, Long[] target) {
        int d[][]; // 矩阵
        int n = str.length;
        int m = target.length;
        int i; // 遍历str的
        int j; // 遍历target的
        Object ch1; // str的
        Object ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str[i - 1];
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target[j - 1];
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    //求三个数中的最小数
    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * acquire the similarity of two objects
     *
     * @param
     * @param target
     * @return
     */
    public static float getSimilarityRatio(String orig, String target) {
        return 1 - (float) compare(orig, target) / Math.max(orig.length(), target.length());
    }

    public static float getSimilarityRatio(Long[] orig, Long[] target) {
        if (orig == null && target == null) {
            return 1;
        }

        if (orig == null || target == null) {
            return 0;
        }

        if (orig.length == 0 && target.length == 0) {
            return 1;
        }
        return 1 - (float) compare(orig, target) / Math.max(orig.length, target.length);
    }

	/**
	 *
	 * @param orig
	 * @param target
	 */
	public void validate(Object orig, Object target) {

	}

    public static void main(String[] args) throws Exception {
        Long[] info0 = {123L, 567L, 45L};
        Long[] info1 = {123L, 12L, 567L};
        log.info("similarityRatio=" + Levenshtein.getSimilarityRatio(info0, info1));
        log.info("similarityRatio=" + Levenshtein.getSimilarityRatio("abcd", "cba"));
    }
}
 
