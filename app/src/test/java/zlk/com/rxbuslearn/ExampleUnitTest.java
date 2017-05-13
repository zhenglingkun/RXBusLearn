package zlk.com.rxbuslearn;

import org.junit.Test;

import zlk.com.rxbuslearn.util.FirstLetterUtil;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void firstLetter() {
        assertEquals("a", FirstLetterUtil.getFirstLetter("啊"));
        assertEquals(" a", FirstLetterUtil.getFirstLetter(" 啊"));
        assertEquals("akk", FirstLetterUtil.getFirstLetter("akk"));
        assertEquals("#kk", FirstLetterUtil.getFirstLetter("#kk"));
        assertEquals("#aassa", FirstLetterUtil.getFirstLetter("#啊啊ss啊"));
    }
}