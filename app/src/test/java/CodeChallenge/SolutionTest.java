package CodeChallenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SolutionTest {

    @Test void shouldReadTextFromStdIn() {
        String string = "the white whale\nthe white whale\nthe white whale\n";
        InputStream stringStream = new java.io.ByteArrayInputStream(string.getBytes());
        List<String> expectedResult = 
            new ArrayList<String>(Arrays.asList(
                new String[] {
                    "the white whale - 3",
                    "whale the white - 2",
                    "white whale the - 2"
                }));
        List<String> result = Solution.parseTextFromStdInPipe(stringStream);
        assertIterableEquals(
            expectedResult, result, 
                "Should read text from InputStream and output parsed and sorted results");
    }
}
