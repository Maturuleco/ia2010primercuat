
public class WordManagerFactory {

	private static String KEY_NAME_AD = "ADJETIVE";
	private static String KEY_NAME_EM = "EMPTY";
	private static String KEY_NAME_EN = "ENTITY";
	
	public static WordManager getWordManager(String name){
		if( name.contains("+") || name.contains("*")){
			name = name.split("\\(")[1]; // elimino el parentesis
		}
			
		if(name.startsWith(KEY_NAME_AD)){
			return AdjetiveWordManager.getInstance();
		}else if(name.startsWith(KEY_NAME_EM)){
			return EmptyWordManager.getInstance();
		}else if(name.startsWith(KEY_NAME_EN)){
			return EntityWordManager.getInstance();
		}
		
		return null;
	}
	
}
