package fr.ufc.l3info.oprog;

import java.util.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map;

import static java.lang.Math.abs;

public class Network {

    Vector<Station> Stations = new Vector<>();


    public Network(){

    }

    public void addStation(Station s){
        if (getStationByName(s.getName())==null) { //Si la staion n'est pas trouvé c'est quel n'existe pas donc on peut l'ajouter
            Stations.add(s);
        }

    }

    public Set<String> getLines(){
        Set<String> hash_Set = new HashSet<>();
        for (int i = 0; i<Stations.size(); i++){
            for(String s : Stations.get(i).getLines())
                if (!hash_Set.contains(s)){
                    hash_Set.add(s);
                }
        }
        return hash_Set;
    }

    public Station getStationByName(String name){
        String n=name.trim();
        for (int i = 0; i<Stations.size(); i++){

            if (Stations.get(i).getName().equals(n))
                return Stations.get(i);
        }
        return null;
    }

    public Station getStationByLineAndNumber(String line, int number){
        Set<String> hash_Set = new HashSet<>();

        for (int i = 0; i<Stations.size(); i++){
            if (Stations.get(i).getNumberForLine(line.trim())==number)
                return Stations.get(i);
        }
        return null;
    }

    public boolean isValid(){
        Set<String> hash_Set = this.getLines();
        if (hash_Set.size()==0){
            return false;
        }
        for (String value : hash_Set){
            Map<Integer, Double> StationbyLine = new TreeMap<>();
            for (Station s : Stations) {
                int nb = s.getNumberForLine(value);
                double dist = s.getDistanceForLine(value);
                if (nb != 0 && dist >= 0) {

                        if (StationbyLine.containsKey(nb)) {
                        return false;
                    } else {
                            StationbyLine.put(nb, dist);
                    }
                 }
            }
            /*
            la clef est censé est trier et ne pas contenir de doublon grace a contains key ligne 63
             */
            //Iterator<Map.Entry<Integer, Double>> it = StationbyLine.entrySet().iterator();
            boolean fir = true;
            //Map.Entry<Integer, Double> current = it.next();
            /*while (it.hasNext()) {
                if (fir){
                    if (current.getKey() != 1 || current.getValue()!= 0.0){
                        return false;
                    }
                    fir =false; //ICI on  ne compare pas les clef car previous = curent
                }
                    if(it.hasNext()){
                        Map.Entry<Integer, Double> after = it.next();
                        if (current.getKey()+1 != after.getKey()|| current.getValue() >= after.getValue()){
                            return false;
                        }
                        current = after;
                    }
            }*/
            int keyprevious = 0;
            double valprevious = -1;
            for (Map.Entry current : StationbyLine.entrySet()) {
                int key = (int) current.getKey();
                double val = (double) current.getValue();
                if (fir){
                    if (key != 1 || val != 0.0){
                        return false;
                    }
                    fir =false; //ICI on  ne compare pas les clef car previous = curent
                    keyprevious = key;
                    valprevious = val;
                }
                else{
                    if (keyprevious+1 != key|| valprevious >= val){
                        return false;
                    }
                    keyprevious = key;
                    valprevious = val;
                }
            }
        }
        /*
        Regarder ici si une station est isolé ?
         */
        return true;
    }

    public double distance(String s1, String s2){
        if (s1==null || s2 == null ){
            return -30;
        }
        s1=s1.trim();
        s2=s2.trim();
        Station s1s = getStationByName(s1);
        Station s2s = getStationByName(s2);

        Set<String> lines1 = s1s.getLines();
        Set<String> lines2 = s2s.getLines();
        Set<String> same = new HashSet<>();

        for (String s : lines1){
            for (String s12 : lines2){
                if (s==s12){
                    same.add(s);
                }
            }
        }
        double dist = 0;
        for (String s : same){
            double d1 =s1s.getDistanceForLine(s);
            double d2 =s2s.getDistanceForLine(s);
            if (abs(d1-d2)<dist || dist == 0){
                dist=abs(d1-d2);
            }
        }
        /*for (Station s : Stations){

        }
        */


        return dist;
    }
}
