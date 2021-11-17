package fr.ufc.l3info.oprog.parser;

import fr.ufc.l3info.oprog.Station;

import java.util.*;

/**
 * Vérification du fichier parsé :
 *  - liste de lignes non vide
 *  - nom de ligne vide
 *  - nom de stations non vides
 *  - noms de stations uniques sur une ligne
 *  - distance kilométrique positive
 *  - distance kilométrique strictement croissante à partir de 0
 *  - toutes les lignes sont accessibles
 */
public class ASTCheckerVisitor implements ASTNodeVisitor {

    private HashMap<String, ERROR_KIND> errors;

    public ASTCheckerVisitor() {
        errors = new HashMap<>();
    }

    public Map<String, ERROR_KIND> getErrors() {
        return new HashMap<>(errors);
    }

    @Override
    public Object visit(ASTNode n) {
        // TODO verification
        return null;
    }

    @Override
    public Object visit(ASTListeLignes n) {
        // TODO verification
        Set<String> hash_Set = new HashSet<>();

        Set<String> ligne [] = new Set[n.getNumChildren()];
        if (n.getNumChildren()<1) {
            errors.put("Ligne list empty", ERROR_KIND.EMPTY_LINE_LIST);
        }
        int count = 0;
        for (ASTNode child : n) {

            Object[] decl = (Object[]) child.accept(this);; //ASTLigne

            if (hash_Set.contains((String) decl[0])){
                errors.put("Double station name :" + (String) decl[0], ERROR_KIND.DUPLICATE_LINE_NAME);
                ligne[count] = new HashSet<>();
            }else {
                hash_Set.add((String) decl[0]);
                ligne[count] = (Set<String>) decl[1];
            }
            count++;


        }


        for (int i = 0; i< ligne.length ; i++){

                boolean unreached = true;

                for (String temp : ligne[i]) {
                    if (temp!=null) {

                        for (int j = 0; j < ligne.length; j++) {
                            if(i != j) {
                                if (ligne[j].contains(temp)) {
                                    unreached = false;
                                }
                            }
                        }

                    }
                    if (unreached) {
                        errors.put("Line number : " + i + " unreached.", ERROR_KIND.UNREACHABLE_LINE);
                        unreached = true;
                    }
                }


        }
        return null;
    }

    @Override
    public Object visit(ASTLigne n) {
        Set<String> hash_Set = new HashSet<>();


        String line = (String)n.getChild(0).accept(this);//ASTChaine nom de la ligne
        if (n.getChild(0).accept(this)==null){
            errors.put("Name of ligne empty", ERROR_KIND.EMPTY_LINE_NAME);
        }
        double precedent = -1.0;

        for (int i=1; i < n.getNumChildren(); i++) {
            Object[] decl = (Object[]) n.getChild(i).accept(this);  //ASTDeclaration
            if (decl != null){
                if (hash_Set.contains((String) decl[0])){
                    errors.put("Double station name :" + (String) decl[0], ERROR_KIND.DUPLICATE_STATION_NAME);
                }else{
                    hash_Set.add((String) decl[0]);
                }
                if ((double) decl[1] <= precedent){
                    errors.put("Km number not in order on the file", ERROR_KIND.WRONG_NUMBER_VALUE);
                }else {
                    precedent = (double) decl[1];
                }
            }



        }
        return new Object[] {line,hash_Set}; // renvoie le nom de la ligne et tout les station presente dans la ligne
    }

    @Override
    public Object visit(ASTDeclaration n) {
        String key = (String) n.getChild(0).accept(this); // ASTChaine
        Double value = (Double) n.getChild(1).accept(this); // ASTNombre
        if (key==null) {
            errors.put("Station name empty after trim", ERROR_KIND.EMPTY_STATION_NAME);
        }
        if (key != null && value != null){
            return new Object[] { key, value };
        }
        return null;
    }

    @Override
    public Object visit(ASTChaine n) {
        // TODO verification

        String key = n.toString().substring(1, n.toString().length()-1);
        if (key.trim().isEmpty()){
            return null;
        }
        return key.trim();
    }

    @Override
    public Object visit(ASTNombre n) {

        double km = n.getNumberValue();
        if (km<0){
            errors.put("Km under 0.0", ERROR_KIND.WRONG_NUMBER_VALUE);
            return null;
        }
        return km;
    }
}

enum ERROR_KIND {
    EMPTY_LINE_LIST,
    EMPTY_LINE_NAME,
    DUPLICATE_LINE_NAME,
    EMPTY_STATION_NAME,
    DUPLICATE_STATION_NAME,
    WRONG_NUMBER_VALUE,
    UNREACHABLE_LINE
}