

public class ArticlesWordManager extends WordManager{

	
	private static ArticlesWordManager instance;

	public ArticlesWordManager() {
		super.loadWords();
	}
	
	public static ArticlesWordManager getInstance(){
		if(instance == null){
			instance = new ArticlesWordManager();
		}
		return instance;
	}
	
	public String getName(){
		return Constant.KEY_NAME_AR;
	}
	
	public String getPath(){
		return "../articlesWords.txt";
	}
}
