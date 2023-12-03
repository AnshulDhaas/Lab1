import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 * (method removePunctuation() was added from teacher code)
 **/
public class Review {

  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();

  private static final String SPACE = " ";

  static {
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while (input.hasNextLine()) {
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0], Double.parseDouble(temp[1]));
        // System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }

    // read in the positive adjectives in postiveAdjectives.txt
    try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while (input.hasNextLine()) {
        String temp = input.nextLine().trim();
        // System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }

    // read in the negative adjectives in negativeAdjectives.txt
    try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while (input.hasNextLine()) {
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    } catch (Exception e) {
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }
  }

  /**
   * returns a string containing all of the text in fileName (including
   * punctuation),
   * with words separated by a single space
   */
  public static String textToString(String fileName) {
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));

      // add 'words' in the file to the string, separated by a single space
      while (input.hasNext()) {
        temp = temp + input.next() + " ";
      }
      input.close();

    } catch (Exception e) {
      System.out.println("Unable to locate " + fileName);
    }
    // make sure to remove any additional space that may have been added at the end
    // of the string.
    return temp.trim();
  }

  /**
   * @returns the sentiment value of word as a number between -1 (very negative)
   *          to 1 (very positive sentiment)
   */
  public static double sentimentVal(String word) {
    try {
      return sentiment.get(word.toLowerCase());
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * Returns the ending punctuation of a string, or the empty string if there is
   * none
   */
  public static String getPunctuation(String word) {
    String punc = "";
    for (int i = word.length() - 1; i >= 0; i--) {
      if (!Character.isLetterOrDigit(word.charAt(i))) {
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

  /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation(String word) {
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(0))) {
      word = word.substring(1);
    }
    while (word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length() - 1))) {
      word = word.substring(0, word.length() - 1);
    }

    return word;
  }

  /**
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and
   * returns it.
   */
  public static String randomPositiveAdj() {
    int index = (int) (Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }

  /**
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and
   * returns it.
   */
  public static String randomNegativeAdj() {
    int index = (int) (Math.random() * negAdjectives.size());
    return negAdjectives.get(index);

  }

  /**
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective() {
    boolean positive = Math.random() < .5;
    if (positive) {
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

  /**
   * Activity 2: totalSentiment()
   * Write the code to total up the sentimentVals of each word in a review.
   */
  public static double totalSentiment(String filename) {     //Anshul wrote this method
      // read in the file contents into a string using the textToString method with
      // the filename

      // set up a sentimentTotal variable
      double sentimentTotal = 0;

      // loop through the file contents
      String reviewText = textToString(filename);
      int spaceIndex = 0;
      while (reviewText.length() > 0) {
          spaceIndex = reviewText.indexOf(" ");
          String word;
          if (spaceIndex >= 0) {
              word = reviewText.substring(0, spaceIndex);
          } else {
              // This is the last word in the string
              word = reviewText;
          }
          word = removePunctuation(word);
          sentimentTotal += sentimentVal(word);
          // Update reviewText to start after this word
          if (spaceIndex >= 0) {
              reviewText = reviewText.substring(spaceIndex + 1);
          } else {
              reviewText = ""; // No more words left
          }
      }
      return sentimentTotal;
  }

  /**
   * Activity 2 starRating method
   * Write the starRating method here which returns the number of stars for the
   * review based on its totalSentiment.
   */
  public static int starRating(String filename) {  //Anshul wrote this method
    // call the totalSentiment method with the fileName

    // determine number of stars between 0 and 4 based on totalSentiment value
    int stars = 0;// change this!
    // write if statements here
    if (totalSentiment(filename) < -10) {
      stars = 0;
      return stars;
    }
    else if (totalSentiment(filename) < 0) {
      stars = 1;
      return stars;
    }
    else if (totalSentiment(filename) < 10) {
      stars=2;
      return stars;
    }
    else if (totalSentiment(filename) < 20) {
      stars=3;
      return stars;
    }
    else if (totalSentiment(filename) < 30) {
      stars=4;
      return stars;
    }
    else {
      stars=5;
      return stars;
    }
  }

  public static String fakeReview(String fileName)    //Anshul wrote this method
  {
    String reviewText = textToString(fileName);
    for (int i = 0; i < reviewText.length(); i++)
      {
        if (reviewText.substring(i, i+1).equals("*"))
        {
          int textSpace= reviewText.indexOf(" ", i);
          int adjLength = reviewText.substring(i, textSpace).length();
          String reviewText1=reviewText.substring(0, i);
          String reviewText2=reviewText.substring(i+adjLength);
          String word = reviewText.substring(i, i+adjLength-1);
          word = removePunctuation(word);
          word = randomPositiveAdj(); //modified randomAdjective to randomPositiveAdj
          reviewText = reviewText1 + word + reviewText2;
        }
      }
    return(reviewText);
  }

  

        
      
      

    // return number of stars
}

