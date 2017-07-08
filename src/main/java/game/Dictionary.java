package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Viktor on 07.07.17.
 */
public class Dictionary implements Iterable<Verb> {

    ArrayList<ArrayList<Verb>> dictionary = new ArrayList<ArrayList<Verb>>();
    ArrayList<String> nameTypes = new ArrayList<String>();


    public Iterator<Verb> iterator() {
        return new Iterator<Verb>() {
            private int cursor = 0;
            private final int size = getSummarySize();

            public boolean hasNext() {
                return cursor < size;
            }
            public Verb next() {
                return getVerb(cursor++);
            }
        };
    }

    public void addVerb (Verb verb, String nameType){
       int index = nameTypes.indexOf(nameType);
       if(index == -1){
           dictionary.add(new ArrayList<Verb>());
           nameTypes.add(nameType);
           index = dictionary.size()-1;
       }
       dictionary.get(index).add(verb);
    }

    public int getSummarySize(){
        int cnt = 0;
        for (List list : dictionary) {
            cnt += list.size();
        }
        return cnt;
    }

    public Verb getVerb(int index){
        for (List<Verb> verbList : dictionary) {
            if(verbList.size() -1 < index) {
                index -= verbList.size();
                continue;
            }
            return verbList.get(index);
        }
        return null;
    }

    public void loadFromFile(InputStream io){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(io));

            String line = "";
            int cnt = 1;
            final int sizeColumn = 4;
            String currentTypeName = "Users verbs";
            while ((line = reader.readLine()) != null) {

                if(line.length() == 0) continue;

                if (line.charAt(0) == '*') {
                    line = line.substring(2, line.length());
                    if(line.split(" ")[0].equals("Type")){
                        continue;
                    }else{
                        currentTypeName = line;
                        continue;
                    }
                }
                addVerb(Verb.parse(line), currentTypeName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getTypeByIndex(int index){
        for (int i = 0; i < dictionary.size(); i++) {
            if(dictionary.get(i).size() < index){
                index -= dictionary.get(i).size();
                continue;
            }
            else return nameTypes.get(i);
        }
        return "NAN";
    }

    public ArrayList<Verb> getListByType(String name){
        int index = nameTypes.indexOf(name);
        if(index == -1) return null;
        return dictionary.get(index);
    }

}
