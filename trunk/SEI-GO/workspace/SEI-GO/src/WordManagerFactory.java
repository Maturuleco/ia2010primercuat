
public class WordManagerFactory {

	
	public static WordManager getWordManager(String name){
		if( name.contains("+") || name.contains("*")){
			name = name.split("\\(")[1]; // elimino el parentesis
		}
			
		if(name.startsWith(Constant.KEY_NAME_AD)){
			return AdjetiveWordManager.getInstance();
		}else if(name.startsWith(Constant.KEY_NAME_EM)){
			return EmptyWordManager.getInstance();
		}else if(name.startsWith(Constant.KEY_NAME_EN)){
			return EntityWordManager.getInstance();
		}else if(name.startsWith(Constant.KEY_NAME_AR)){
			return ArticlesWordManager.getInstance();
		}else if(name.startsWith(Constant.KEY_NAME_VE)){
			return VerbWordManager.getInstance();
		}
		
		return null;
	}
	
}
