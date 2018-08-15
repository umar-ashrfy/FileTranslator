import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 
 * @author Umar Ashrfy
 *
 */
public class Translate {
	
	/**
	 * Entry point Main class 
	 */
	public static void main(String[] args) {
		try {
			File inputfile = new File("C:\\sample-input.txt");
			File outputFile = new File("C:\\sample-output.txt");

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile), "UTF8"));
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF8"));

			String str;
			String outString = "";
			String jpString = "";
			while ((str = in.readLine()) != null) {
				if (str.indexOf("msgstr") > -1 && !hasJapanese(str)) {
					String msgStr = str.substring(8, str.length() - 1);
					jpString = translate("en", "ja", msgStr);
					outString = "msgstr \"" + jpString + "\"";
				} else {
					outString = str;
				}
				out.append(outString).append("\r\n");
			}

			in.close();
			out.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Check if the string is already in japanese
	 */
	public static boolean hasJapanese(CharSequence charSequence) {
		boolean hasJapanese = false;
		for (char c : charSequence.toString().toCharArray()) {
			if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HIRAGANA
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
				hasJapanese = true;
				break;
			}
		}

		return hasJapanese;
	}

	/**
	 * Calles the Google SCRIPT and translate text
	 */
	private static String translate(String langFrom, String langTo, String text) throws IOException {
		String yourURL = "https://script.google.com/macros/s/AKfycbw_vOQSOIEZiW3crJCY4eqrROoj8-PqDofSCcEq7c4hGEwcIDU/exec";
		String urlStr = yourURL + "?q=" + URLEncoder.encode(text, "UTF-8") + "&target=" + langTo + "&source="
				+ langFrom;
		URL url = new URL(urlStr);
		StringBuilder response = new StringBuilder();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}