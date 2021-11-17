package fr.ufc.l3info.oprog;



import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * Class representing a station.
 */
public class Station {
    String nom;
    //List LinesTab = new ArrayList();
    Map<String, Vector> LinesMap = new HashMap<>();





    /** Builds a station associated to no lines.
     * @param _name the name of the station.
     */
    public Station(String _name) {
        nom=_name.trim();
    }

    /**
     * Builds a station, initially associated to a given line with a given number.
     * @param _name the name of the station
     * @param _line the name of the line associated to the station
     * @param _number the number of the station on the considered line
     * @param _dist the distance of the station on the considered line
     */
    public Station(String _name, String _line, int _number, double _dist) {
        nom = _name.trim();
        addLine(_line,_number,_dist);
        /*LinesMap.put(_line,new Vector());
        LinesMap.get(_line).add(_number);
        LinesMap.get(_line).add(_dist);*/
    }


    /**
     * Adds a line to the current station, with the appropriate parameters.
     * If the line already exists, the previous information are overwritten.
     * @param _line the name of the line associated to the station
     * @param _number the number of the station on the considered line
     * @param _dist the distance of the station on the considered line
     */
    public void addLine(String _line, int _number, double _dist) {
        /**
         * faire le trim
         */
        if (_line == null || _number <= 0 || _dist <0.0 || _line.trim()==""){
            return;
        }
        String l = _line.trim();
        if(LinesMap.containsKey(l)) { //regarde si la line existe deja
            LinesMap.get(l).set(0,_number);
            LinesMap.get(l).set(1,_dist);
        }
        //si la ligne existe pas elle est crée
        LinesMap.put(l,new Vector());
        LinesMap.get(l).add(_number);
        LinesMap.get(l).add(_dist);
    }


    /**
     * Removes a line from the station.
     * @param _line the line to remove.
     */
    public void removeLine(String _line) {
        if (_line==null){
            return;
        }
        String l = _line.trim();
        if (!(LinesMap.containsKey(l))){ //Peut etre try catch
            return;
        }
        LinesMap.get(l).clear();
        System.gc();
        LinesMap.remove(l);

    }


    /**
     * Retrieves the name of the station.
     * @return the name of the station
     */
    public String getName() {
        return nom;
    }

    /**
     * Returns the number of the station on a given line.
     * @param l The name of the line
     * @return the # of the station for the given line,
     *         or 0 if the line does not exist at the station.
     */
    public int getNumberForLine(String l) {
        if (LinesMap.containsKey(l.trim())){
            return (int) LinesMap.get(l.trim()).get(0);
        }
        return 0;
    }


    /**
     * Returns the distance of the station on a given line.
     * @param l The name of the line.
     * @return the distance of the station w.r.t. the beginning of the line.
     */
    public double getDistanceForLine(String l) {
        if (LinesMap.containsKey(l.trim())){
            return (double) LinesMap.get(l.trim()).get(1);
        }
        return -1.0;
    }

    /**
     * Computes the set of lines associated to the station.
     * @return a set containing the names of the lines that cross the station.
     */
    public Set<String> getLines() {
        return LinesMap.keySet();
    }


    @Override
    public int hashCode() {
        //return Integer.parseInt(this.nom);   // TODO
        final int prime = 31;
        int result = 1;
        result = prime * result
                + //((this.nom == null) ? 0 :
         this.nom.hashCode()/*)*/;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null) return false;
        if (!(o instanceof Station))
            return false;
        if (o == this)
            return true;
        return this.hashCode() == ((Station)o).hashCode();
    }
}