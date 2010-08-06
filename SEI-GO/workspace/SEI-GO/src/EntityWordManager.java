

public class EntityWordManager extends WordManager{

	/**
	 * Pueden ser nombre de platos, lugares etc.  
	 */
	private static EntityWordManager instance;
	
	public EntityWordManager() {
		super.loadWords();
	}
	
	public static EntityWordManager getInstance(){
		if(instance == null){
			instance = new EntityWordManager();
		}
		return instance;
	}
	
	public String getName(){
		return Constant.KEY_NAME_EN;
	}
	
	public String getPath(){
		return "../entityWords.txt";
	}
}
