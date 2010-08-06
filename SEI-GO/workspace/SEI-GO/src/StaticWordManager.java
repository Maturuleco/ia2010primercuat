

public class StaticWordManager extends WordManager{

	
	private static StaticWordManager instance;
	private String name = Constant.KEY_NAME_ST;
	
	public static StaticWordManager getInstance(){
		if(instance == null){
			instance = new StaticWordManager();
		}
		return instance;
	}
	
	public Boolean containWord(String word){
		return name.equals(word);
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getPath(){
		return "";
	}
	
}
