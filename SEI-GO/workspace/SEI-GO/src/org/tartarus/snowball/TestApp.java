
package org.tartarus.snowball;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class TestApp {
    
    public static void main(String [] args) throws Throwable {

    Class stemClass = Class.forName("org.tartarus.snowball.ext.spanishStemmer");
    //Class stemClass = Class.forName("org.tartarus.snowball.ext.porterStemmer");
			//args[0] + "Stemmer");
    SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

	Reader reader;
	reader = new InputStreamReader(new FileInputStream("..\\words.txt"));
	reader = new BufferedReader(reader);

	StringBuffer input = new StringBuffer();
    OutputStream outstream;

	outstream = new FileOutputStream("..\\ewords.txt");
	Writer output = new OutputStreamWriter(outstream);
	output = new BufferedWriter(output);

	int repeat = 1;
	Object [] emptyArgs = new Object[0];
	int character;
	while ((character = reader.read()) != -1) {
	    char ch = (char) character;
	    if (Character.isWhitespace((char) ch)) {
		if (input.length() > 0) {
		    stemmer.setCurrent(input.toString());
		    for (int i = repeat; i != 0; i--) {
		    	stemmer.stem();
		    }
		    output.write(stemmer.getCurrent());
		    output.write('\n');
		    input.delete(0, input.length());
		}
	    } else {
		input.append(Character.toLowerCase(ch));
	    }
	}
	output.flush();
    }
}