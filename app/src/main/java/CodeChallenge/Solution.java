package CodeChallenge;

import java.io.*;
import java.util.List;
import java.util.Map;

class Solution {

    public static void main(String[] args) {       
        if(args.length == 0) {
            // No files entered, assuming pipe from stdin
            parseTextFromStdInPipe(System.in);
        }
        else {
            parseFilePathsFromArgs(args);
        }
               
    }

    public static BufferedReader getInput(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader;
    }

    public static List<String> parseTextFromStdInPipe(InputStream inputStream) {
        WordSequenceParser sequenceParser = new WordSequenceParser(); 
        BufferedReader reader = getInput(inputStream);
        sequenceParser.parseWordSequencePerLine(reader);
        List<Map.Entry<String, Integer>> sortedSequences = 
            sequenceParser.sortTopOneHundredSequences();
        //System.out.println(sortedSequences);
        return sequenceParser.printTopOneHundredSequences(sortedSequences);
    }

    public static void parseFilePathsFromArgs(String[] filePaths) {
        WordSequenceParser sequenceParser = new WordSequenceParser(); 
        sequenceParser.executeAllFutures(
            sequenceParser.createFuturesToParseFromFiles(filePaths));
        List<Map.Entry<String, Integer>> sortedSequences = 
            sequenceParser.sortTopOneHundredSequences();
        sequenceParser.printTopOneHundredSequences(sortedSequences);
    }

}

