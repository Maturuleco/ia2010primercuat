

public class EmptyWordManager extends WordManager{

	
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
		return ".".equals(word) || 
			   ",".equals(word) || 
			   "y".equals(word) || 
			   "(".equals(word) || 
			   ")".equals(word);
	}

	public String getName(){
		return Constant.KEY_NAME_EM;
	}
	
	public String getPath(){
		return "";
	}
	
}
