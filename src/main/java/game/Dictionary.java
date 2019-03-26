package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Dictionary implements Iterable<Verb> {

    private ArrayList<ArrayList<Verb>> verbDictionary = new ArrayList<ArrayList<Verb>>();
    private ArrayList<String> nameTypes = new ArrayList<String>();

    public Dictionary(){};

    public Dictionary(Dictionary dictionary) {
        for (ArrayList<Verb> verbArrayList : dictionary.verbDictionary) {
            this.verbDictionary.add(new ArrayList<>(verbArrayList));
        }
        this.nameTypes = new ArrayList<>(dictionary.nameTypes);
    }


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
           verbDictionary.add(new ArrayList<Verb>());
           nameTypes.add(nameType);
           index = verbDictionary.size()-1;
       }
       verbDictionary.get(index).add(verb);
    }

    public int getSummarySize(){
        int cnt = 0;
        for (List list : verbDictionary) {
            cnt += list.size();
        }
        return cnt;
    }

    public Verb getVerb(int index){
        for (List<Verb> verbList : verbDictionary) {
            if(verbList.size() -1 < index) {
                index -= verbList.size();
                continue;
            }
            return verbList.get(index);
        }
        return null;
    }

    public void loadFromResource(String path) throws FileNotFoundException, IllegalAccessException {
        String parser[] = path.split("/");
        String input = "/" + parser[parser.length-1];
        loadFromInputStream(new InputStreamReader(getClass().getResourceAsStream(input)));
    }

    public void loadFromFile(String path) throws FileNotFoundException, IllegalAccessException {
        loadFromInputStream(new FileReader(path));
    }

   private void loadFromInputStream(InputStreamReader inputStreamReader) throws FileNotFoundException, IllegalAccessException {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(inputStreamReader);

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
            throw e;
        } catch (IOException e) {
            throw  new IllegalAccessException("Error parse");
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getTypeByIndex(int index){

        for (int i = 0; i < verbDictionary.size(); i++) {
            if(verbDictionary.get(i).size() <= index){
                index -= verbDictionary.get(i).size();
                continue;
            }
            else return nameTypes.get(i);
        }
        return "NAN";
    }

    public ArrayList<Verb> getListByType(String name){
        int index = nameTypes.indexOf(name);
        if(index == -1) return null;
        return verbDictionary.get(index);
    }

    public int indexOf(Verb v){
        int index = -1;
        int i = 0;
        for (Verb verb : this) {
            if(v.equals(verb))
                index = i;
            i++;
        }

        return index;
    }

    public ArrayList<Verb> getVerben() {
        ArrayList<Verb> res = new ArrayList<>();
        this.forEach(verb -> res.add(verb));
        return res;
    }

    public ArrayList<String> getNameTypes() {
        return nameTypes;
    }

    public Dictionary clone(){
        return new Dictionary(this);
    }

    public void saveToFile(File inputFile) throws IOException {
        BufferedWriter writer = null;
        try {
             writer = new BufferedWriter(new FileWriter(inputFile.getPath()));
            for (int i = 0; i < nameTypes.size(); i++) {
                writer.write("* Type " + (i+1) + "\n");
                writer.write("* " + nameTypes.get(i) + "\n");
                for (Verb verb : verbDictionary.get(i)) {
                    writer.write(verb.getInf() + " " + verb.getThird() + " " + verb.getPrat() + " " + verb.getP2() + "\n");
                }
                writer.write("\n");
            }

        } catch (IOException e) {
            throw e;
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addVerbFromDictionary(Dictionary dictionary){
        for (int i = 0; i < dictionary.verbDictionary.size(); i++) {
            for (Verb verb : dictionary.verbDictionary.get(i)) {
                addVerb(verb, dictionary.getNameTypes().get(i));
            }
        }
    }
}
