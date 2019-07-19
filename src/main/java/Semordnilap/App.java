/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Semordnilap;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class App {

    public static final String wiktionaryUrl = "https://en.wiktionary.org/wiki/";

    public static void main(String[] args) {
        boolean doContinue = true;
        while (doContinue) {
            System.out.println("Welcome to Semordnilap. Please enter a word:");
            System.out.println(isPalindromeOrSemordnilap(readString()));
        }
    }

    private static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Determines whether a given string is a palindrome or a semordnilap.
     * @param string
     * @return String
     */
    public static String isPalindromeOrSemordnilap(String string) {
        String reverse = new StringBuilder(string).reverse().toString();
        if(reverse.equalsIgnoreCase(string) && doesWordExistInDict(reverse.toLowerCase()))  {
            return "That's a palindrome!";
        } else if (doesWordExistInDict(reverse.toLowerCase())) {
            return "That's a semordnilap!";
        }
        return "That's not a palindrome or semordnilap...";
    }

    /**
     * Calls wiktionary to determine whether the word exists or not.
     * @param string
     * @return boolean
     */
    public static boolean doesWordExistInDict(String string) {
        try {
            URL url = new URL(wiktionaryUrl + string);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            System.out.println(getStream(urlConnection));
            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            if(statusCode == 200) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getStream(HttpsURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }
}
