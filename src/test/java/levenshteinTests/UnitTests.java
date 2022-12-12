package levenshteinTests;

import com.bj.search.common.Levenshtein;
import org.junit.Test;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public class UnitTests {

    @Test
    public void testString(){
        String str1 = "";
        String str2 = "this is a new world";
        float res = Levenshtein.getSimilarityRatio(str1, str2);
        assert res >= 0;
        System.out.println("similarityRatio=" + res);
    }

    @Test
    public void testIntegers(){
        Long[] info0 = {123L, 567L, 45L};
        Long[] info1 = {123L, 12L, 567L};
        float res = Levenshtein.getSimilarityRatio(info0, info1);
        assert res >= 0;
        System.out.println("similarityRatio=" + res);
    }
}
