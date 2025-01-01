package api.payload;

public class Video {

    private String id;
    private String category;
    private String name;
    private String rating;
    private String releaseDate;
    private int reviewScore;

  

	// Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    // Overriding toString for better printout in the test
    @Override
    public String toString() {
        return "Video{id='" + id + "', category='" + category + "', name='" + name + "', rating='" + rating + "', releaseDate='" + releaseDate + "', reviewScore=" + reviewScore + "}";
    }
}
