package fr.ufc.l3info.oprog;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Barrier {
    Network network;
    String station;
    Map<Double,Integer> prices;
    private Barrier(Network net, String station, Map<Double,Integer> prices){
        this.network=net;
        this.station=station;
        this.prices=prices;
    }
    public static Barrier build(Network n, String s, Map<Double,Integer> p){
        if (n==null || s ==null || p == null){
            return null;
        }
        if (!n.isValid() || n.getStationByName(s)==null){
            return null;
        }
        if(!p.containsKey(0.0)){
            return null;
        }

        for (Map.Entry<Double, Integer> entry : p.entrySet()) {
            Integer v = entry.getValue();
            if (v < 0 || entry.getKey() < 0) {
                return null;
            }
        }

        return new Barrier(n,s,p);
    }
    public boolean enter(ITicket t){
        if (!t.isValid() ||  t.getAmount() <= 0){
            return false;
        }
        if (t.getEntryStation() != null ){
            t.entering(this.station);
            return false;
        }
        t.entering(this.station);
        return true;
    }
    public boolean exit(ITicket t){
        if (!t.isValid() || t.getEntryStation() == null ){
            return false;
        }
        double dist = this.network.distance(t.getEntryStation(), this.station);

        Iterator<Map.Entry<Double, Integer>> it = prices.entrySet().iterator();
        Map.Entry<Double,Integer> curr = it.next();

        while (curr.getKey() < dist && it.hasNext()) {
            curr = it.next();
        }

        if (t.isChild()){
            if (curr.getValue()/2 <= t.getAmount()){
                t.invalidate();
                return true;
            }

        }else{
            if (curr.getValue() <= t.getAmount()){
                t.invalidate();
                return true;
            }

        }
        return false;


    }
}
