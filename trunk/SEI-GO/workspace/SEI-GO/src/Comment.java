import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Comment {

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	
	private String resto;
	private String serviceRank;
	private String foodRank;
	private String environmentRank;
	private Date date;
	private String user;
	private String text;
	private HashMap<String, Integer> words;
	private List<String> ruleSentences;
	
	public Comment(String resto, String serviceRank, String foodRank, String environmentRank,
			Date date, String user, String text, HashMap<String, Integer> words) {
		super();
		this.resto = resto;
		this.serviceRank = serviceRank;
		this.foodRank = foodRank;
		this.environmentRank = environmentRank;
		this.date = date;
		this.user = user;
		this.text = text;
		this.words = words;
	}
	
	public String getResto() {
		return resto;
	}
	public void setResto(String resto) {
		this.resto = resto;
	}
	public String getServiceRank() {
		return serviceRank;
	}
	public void setServiceRank(String serviceRank) {
		this.serviceRank = serviceRank;
	}
	public String getFoodRank() {
		return foodRank;
	}
	public void setFoodRank(String foodRank) {
		this.foodRank = foodRank;
	}
	public String getEnvironmentRank() {
		return environmentRank;
	}
	public void setEnvironmentRank(String environmentRank) {
		this.environmentRank = environmentRank;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public HashMap<String, Integer> getWords() {
		if( words == null ){
			words = new HashMap<String, Integer>();
		}
		return words;
	}
	public void setWords(HashMap<String, Integer> words) {
		this.words = words;
	}

	public List<String> getRuleSentences() {
		return ruleSentences;
	}

	public void setRuleSentences(List<String> ruleSentences) {
		this.ruleSentences = ruleSentences;
	}
	
	@Override
	public String toString() {
		String comment = "";
		comment = "<"+resto + ", service: "+ serviceRank+ ", food: "+ foodRank+", enviroment: "+environmentRank+", date: "+formatter.format(date)+">\n";
		comment += "Texto orig: " + text;
		
		return comment;
	}


	
	
}
