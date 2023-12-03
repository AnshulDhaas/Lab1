
class Main {
  public static void main(String[] args) {
    // Activity 1: Call the sentimentVal method in Review with a word like
    // "terrible" and print out the result
    double value = Review.totalSentiment("newreview.txt");
    int rating=Review.starRating("newreview.txt");
    String fakereview=Review.fakeReview("simpleReview.txt");
    System.out.println(value);
    System.out.println(rating);
    System.out.println(fakereview);

  }
}
