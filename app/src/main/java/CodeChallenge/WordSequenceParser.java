package CodeChallenge;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.io.*;


class WordSequenceParser {

    // Create a thread safe synchronizedMap to allow multiple futures to access the map safely. 
    private Map<String, Integer> sequenceCountMap = 
        Collections.synchronizedMap(new HashMap<String, Integer>());

    public Map<String, Integer> getSequenceCountMap() {
        return this.sequenceCountMap;
    }

    public LinkedList<String> parseThreeWordSequencesFromString(
        String stringToParse, LinkedList<String> existingWordsList) {
            String[] wordsArray = splitStringIntoWordsArray(stringToParse);
            existingWordsList.addAll(convertWordsArrayToLinkedList(wordsArray));
            while(existingWordsList.size() >= 3) {
                String tempWordSequence = String.join(" ", existingWordsList.subList(0, 3));
                sequenceCountMap.computeIfPresent(tempWordSequence, (key, val) -> val + 1);
                sequenceCountMap.computeIfAbsent(tempWordSequence, val -> 1);
                existingWordsList.removeFirst();
            }
            return existingWordsList;     
    }

    public String[] splitStringIntoWordsArray(String stringToSplit) {
        String lowerCaseString = stringToSplit.toLowerCase();
        // Regex pattern will split on non word or digit unicode characters as well as not ' and not ’
        // https://www.regular-expressions.info/unicode.html
        String[] wordsArray = lowerCaseString.split("[^\\p{L}\\p{Nd}'’]+", 0);
        return wordsArray;
    }

    public LinkedList<String> convertWordsArrayToLinkedList(String[] wordsArray) {
        LinkedList<String> wordsList = new LinkedList<String>();
        Collections.addAll(wordsList, wordsArray);
        wordsList.removeAll(Collections.singleton("")); // remove empty strings
        return wordsList;
    }

    public void parseWordSequencePerLine(BufferedReader reader) {
        String line;
        try{
            LinkedList<String> remainingWordsList = new LinkedList<String>();
            while ((line = reader.readLine()) != null) {
                remainingWordsList = 
                    parseThreeWordSequencesFromString(line, remainingWordsList);
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    public List<CompletableFuture> createFuturesToParseFromFiles(String[] args) {
        List<CompletableFuture> filesToParseAsFutures = 
            new ArrayList<CompletableFuture>();
        for(String arg: args) {
            CompletableFuture<Void> parseSequencesFromSingleFile = 
                CompletableFuture.runAsync(() -> {
                    File file = new File(arg);
                    try {
                        try {
                            BufferedReader reader = new BufferedReader(
                                new InputStreamReader(new FileInputStream(file), "UTF-8"));
                            parseWordSequencePerLine(reader);
                        }
                        catch (UnsupportedEncodingException e) {
                            System.out.println(e);
                        }
                        
                    }
                    catch(FileNotFoundException e) {
                        System.out.println(e);
                    }
            });
            filesToParseAsFutures.add(parseSequencesFromSingleFile);
        }
        return filesToParseAsFutures;
    }

    public void executeAllFutures(List<CompletableFuture> filesToParseAsFutures) {
        CompletableFuture<Void> parseAllFiles = CompletableFuture.allOf(
            filesToParseAsFutures.toArray(new CompletableFuture[0]));
        try{
            try {
                parseAllFiles.get();
            }
            catch (ExecutionException e) {
                System.out.println(e);
            }
        }
        catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Map.Entry<String, Integer>> sortTopOneHundredSequences() {
        if(sequenceCountMap.size() > 0) {
            // Sort Map entries by value in descending order and save output to a List
            List<Map.Entry<String, Integer>> sortedSequenceCountByValuesList = 
                sequenceCountMap.entrySet().stream()
                        .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())) // Minus sign allows sorting in decsending order
                        .collect(Collectors.toList());
            int endIndex = 
                sortedSequenceCountByValuesList.size() < 100 ? sortedSequenceCountByValuesList.size():100;
            return sortedSequenceCountByValuesList.subList(0, endIndex);
        }
        else {
            return new ArrayList<Map.Entry<String, Integer>>();
        } 
    }

    public List<String> printTopOneHundredSequences(List<Map.Entry<String, Integer>> sortedSequenceCount) {
        List<String> outputStrings = new LinkedList<String>();
        // Print first 100 entries of our sorted List of Map entires
        for(Map.Entry<String, Integer> entry: sortedSequenceCount) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            String combinedString = key + " - " + value;
            outputStrings.add(combinedString);
            System.out.println(combinedString);
        }
        return outputStrings;
    }

}

