package levenshteinTests;

import com.bj.search.common.Levenshtein;
import org.junit.Test;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public class ExceptionTests {
    @Test
    public void testStringException(){
        String str1 = "";
        String str2 = null;
        float res = Levenshtein.getSimilarityRatio(str1, str2);
        assert res == 0;
        System.out.println("similarityRatio=" + res);
    }
}
