import java.util.LinkedList;
import java.util.List;


public class WordManagerFactory {

	
	public static WordManager getWordManager(String name){
		/*if( name.contains("+") || name.contains("*")){
			name = name.split("\\(")[1]; // elimino el parentesis
		}*/
			
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
		}else if(name.startsWith(Constant.KEY_NAME_MG)){
			return MagicWordManager.getInstance();
		}else{
			StaticWordManager swm = StaticWordManager.getInstance();
			swm.setName(name.trim().split("\\)")[0]);
			return swm;
		}
		
	}
	
	public static List<WordManager> getWordManagers(String name){
		
		List<WordManager> result = new LinkedList<WordManager>();
		
		if( name.contains("+") || name.contains("*")){
			name = name.split("\\(")[1]; // elimino el parentesis
		} else if (name.contains("\\|") ) {
			name = name.split("\\(")[1]; // elimino el parentesis
		}
		
		String[] names = name.split("\\|");
			
		for (int i = 0; i < names.length; i++) {
			String wordName = names[i];
			WordManager wm = getWordManager(wordName);
			result.add(wm);
			
		}	
		return result;
	}
}
