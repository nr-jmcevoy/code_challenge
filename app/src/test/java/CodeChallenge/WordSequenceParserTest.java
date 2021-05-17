package CodeChallenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

class WordSequenceParserTest {

    WordSequenceParser sequenceParser;

    @BeforeEach void init() {
        this.sequenceParser = new WordSequenceParser();
    }

    @Test void splitASingleWord() {
        String oneWordString = "whale";
        String[] expectedResult = new String[] {"whale"};
        String[] result = sequenceParser.splitStringIntoWordsArray(oneWordString);
        assertArrayEquals(expectedResult, result);
    }

    @Test void splitASingleUppercaseWord() {
        String oneWordString = "WHALE";
        String[] expectedResult = new String[] {"whale"};
        String[] result = sequenceParser.splitStringIntoWordsArray(oneWordString);
        assertArrayEquals(expectedResult, result);
    }

    @Test void splitASingleDigitWord() {
        String oneDigitString = "5";
        String[] expectedResult = new String[] {"5"};
        String[] result = sequenceParser.splitStringIntoWordsArray(oneDigitString);
        assertArrayEquals(expectedResult, result);
    }

    @Test void splitAThreeDigitWord() {
        String threeDigitString = "1 2 3";
        String[] expectedResult = new String[] {"1", "2", "3"};
        String[] result = sequenceParser.splitStringIntoWordsArray(threeDigitString);
        assertArrayEquals(expectedResult, result);
    }

    @Test void splitFiveWordsMixDigitWithChar() {
        String mixString = "1 two 3 four 5";
        String[] expectedResult = new String[] {"1", "two", "3", "four", "5"};
        String[] result = sequenceParser.splitStringIntoWordsArray(mixString);
        assertArrayEquals(expectedResult, result);
    }

    @Test void splitThreeWordsOnWhitespace() {
        String[] expectedResult = new String[] {"the", "white", "whale"};
        String[] whiteSpace = new String[] {" ","\n","\r","\t","\f"};
        String threeWordString;
        for (String characterString: whiteSpace) {
            threeWordString = String.join(characterString, expectedResult);
            String[] result = sequenceParser.splitStringIntoWordsArray(threeWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitThreeWordsOnDoubleWhitespace() {
        String[] expectedResult = new String[] {"the", "white", "whale"};
        String[] whiteSpace = new String[] {" ","\n","\r","\t","\f"};
        String threeWordString;
        for (String characterString: whiteSpace) {
            threeWordString = String.join(characterString + characterString, expectedResult);
            String[] result = sequenceParser.splitStringIntoWordsArray(threeWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitThreeWordsWithTrailingWhitespace() {
        String[] expectedResult = new String[] {"the", "white", "whale"};
        String[] whiteSpace = new String[] {" ","\n","\r","\t","\f"};
        String threeWordString;
        for (String characterString: whiteSpace) {
            threeWordString = "the white whale" + characterString;
            String[] result = sequenceParser.splitStringIntoWordsArray(threeWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitThreeWordsOnPunctuation() {
        String[] expectedResult = new String[] {"the", "white", "whale"};
        String[] punctuation = new String[] {"!","\"","#","$","%","&","(",")","*","+",",","-",
        ".","/",":",";","<","=",">","?","@","[","\\]","^","_","{","|","}","~"};
        String threeWordString;
        for (String characterString: punctuation) {
            threeWordString = String.join(characterString, expectedResult);
            String[] result = sequenceParser.splitStringIntoWordsArray(threeWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitThreeWordsOnDoublePunctuation() {
        String[] expectedResult = new String[] {"the", "white", "whale"};
        String[] punctuation = new String[] {"!","\"","#","$","%","&","(",")","*","+",",","-",
        ".","/",":",";","<","=",">","?","@","[","\\]","^","_","{","|","}","~"};
        String threeWordString;
        for (String characterString: punctuation) {
            threeWordString = String.join(characterString + characterString, expectedResult);
            String[] result = sequenceParser.splitStringIntoWordsArray(threeWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitThreeWordsOnTrailingPunctuation() {
        String[] expectedResult = new String[] {"the", "white", "whale"};
        String[] punctuation = new String[] {"!","\"","#","$","%","&","(",")","*","+",",","-",
        ".","/",":",";","<","=",">","?","@","[","\\]","^","_","{","|","}","~"};
        String threeWordString;
        for (String characterString: punctuation) {
            threeWordString = "the white whale" + characterString;
            String[] result = sequenceParser.splitStringIntoWordsArray(threeWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitWordsWithContractions() {
        String[] contractionCharacters = new String[] {"'", "’"};
        for (String characterString: contractionCharacters) {
            String[] expectedResult = 
                new String[] {"the", "white", "whale"+characterString+"s", "fin"};
            String contractionWordString = "the white whale"+characterString+"s fin";
            String[] result = 
                sequenceParser.splitStringIntoWordsArray(contractionWordString);
            assertArrayEquals(expectedResult, result, 
                "String did not split on " + characterString + " character");
        }
    }

    @Test void splitWordsWithUnicodeGerman() {
        String[] expectedResult = new String[] {"trendl", "begrüßt", "zudem",
            "das", "jahr", "der", "familie"};
        String germanUnicodeWordString = 
            "Trendl begrüßt zudem das \"Jahr der Familie\"";
        String[] result = 
            sequenceParser.splitStringIntoWordsArray(germanUnicodeWordString);
        assertArrayEquals(expectedResult, result, 
            "String did not split correctly with German Unicode characters");
    }

    @Test void parseSequenceFromThreeWordString() {
        String threeWordString = "the white whale";
        LinkedList<String> expectedResult = 
            new LinkedList<String>(Arrays.asList(new String[] {"white", "whale"}));
        LinkedList<String> result = 
            sequenceParser.parseThreeWordSequencesFromString(
                threeWordString, new LinkedList<String>());
        assertIterableEquals(
            expectedResult, result, 
                "Should return a LinkedList containing remaining 2 words");
    }

    @Test void parseSequenceWithLeftoverWords() {
        String threeWordString = "the white whale";
        LinkedList<String> remainingWordsFromPreviousLine = 
            new LinkedList<String>(Arrays.asList(new String[] {"i", "found"}));
        LinkedList<String> expectedResult = 
            new LinkedList<String>(Arrays.asList(new String[] {"white", "whale"}));
        LinkedList<String> result = 
            sequenceParser.parseThreeWordSequencesFromString(
                threeWordString, new LinkedList<String>());
        assertIterableEquals(
            expectedResult, result, 
                "Should return a LinkedList containing remaining 2 words");
    }

    @Test void confirmThreeWordSequenceParsed() {
        String threeWordString = "the white whale";
        sequenceParser.parseThreeWordSequencesFromString(
                threeWordString, new LinkedList<String>());
        Map<String, Integer> sequenceCount = sequenceParser.getSequenceCountMap();
        Integer count = sequenceCount.get(threeWordString);
        assertNotNull(count, "The word sequence key should exist and not equal null");
        assertEquals(count, 1, "The count for this sequence should equal 1");
    }

    @Test void confirmTwoWordSequenceDoesNotParse() {
        String twoWordString = "the white";
        sequenceParser.parseThreeWordSequencesFromString(
                twoWordString, new LinkedList<String>());
        Map<String, Integer> sequenceCount = sequenceParser.getSequenceCountMap();
        Integer count = sequenceCount.get(twoWordString);
        assertNull(count, "The word sequence key should not exist and should return null");
    }

    @Test void confirmDoubleSameWordSequenceIsCounted() {
        String singleSequence = "the white whale";
        String doubleSequenceString = singleSequence+" "+singleSequence;
        sequenceParser.parseThreeWordSequencesFromString(
                doubleSequenceString, new LinkedList<String>());
        Map<String, Integer> sequenceCount = sequenceParser.getSequenceCountMap();
        Integer count = sequenceCount.get(singleSequence);
        assertNotNull(count, "The word sequence key should exist and not equal null");
        assertEquals(count, 2, "The count for this sequence should equal 2");
    }

    @Test void confirmSubSequencesAreCounted() {
        String sixWordString  = "thou not chase the white whale";
        String[] sequences = new String[] {
            "thou not chase",
            "not chase the",
            "chase the white",
            "the white whale"
        };
        sequenceParser.parseThreeWordSequencesFromString(
                sixWordString, new LinkedList<String>());
        Map<String, Integer> sequenceCount = sequenceParser.getSequenceCountMap();
        for(String sequence: sequences) {
            Integer count = sequenceCount.get(sequence);
            assertNotNull(count, "The word sequence key should exist and not equal null");
            assertEquals(count, 1, "The count for this sequence should equal 1");
        }
    }

}
