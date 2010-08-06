

public class EmptyWordManager implements WordManager{

	
	private static String KEY_NAME = "EMPTY";
	private static EmptyWordManager instance;
	
	public static EmptyWordManager getInstance(){
		if(instance == null){
			instance = new EmptyWordManager();
		}
		return instance;
	}
	
	public Boolean containWord(String word){
		Boolean containWord = !AdjetiveWordManager.getInstance().containWord(word) && 
							  !EntityWordManager.getInstance().containWord(word) &&
							  !VerbWordManager.getInstance().containWord(word) &&
							  !ArticlesWordManager.getInstance().containWord(word) && !sign(word);
		return containWord;
	}
	
	private boolean sign(String word) {
		return ".".equals(word);
	}

	public String getName(){
		return KEY_NAME;
	}
	
}
