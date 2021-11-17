package fr.ufc.l3info.oprog;
import org.junit.Assert;
//import org.junit.Before;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class TestNetwork {
    Network n;
    Station s;
    @Before
    public void Constructor() {
        n = new Network();
        Assert.assertNotNull(n);
        s = new Station("ma Station", "toto",1,0.0);

    }
    /*
    Peut etre penser a ajouter des station sans ligne
     */
    @Test
    public void AddStation(){
        n.addStation(s);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(n.getStationByName(s.getName()), s);
        Assert.assertEquals(n.getLines(),hash_Set);
    }
    @Test
    public void AddStationWithoutLine(){
        Station s2;
        s2 = new Station("ma Station");
        n.addStation(s2);
        Assert.assertEquals(n.getStationByName(s2.getName()), s2);
        Set<String> hash_Set = new HashSet<>();
        Assert.assertEquals(n.getLines(),hash_Set);
    }
    @Test
    public void AddExistingStation(){
        n.addStation(s);
        Station s2;
        s2 = new Station("ma Station", "toto2",1,0.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertNotEquals(n.getLines(),hash_Set);
    }
    @Test
    public void AddMultipleStation(){
        n.addStation(s);
        Assert.assertEquals(n.getStationByName(s.getName()), s);
        Station s2;
        s2 = new Station("Station2", "toto",2,10.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(n.getLines(),hash_Set);
        Assert.assertEquals(n.getStationByName(s.getName()), s);
        Assert.assertEquals(n.getStationByName(s2.getName()), s2);
    }
    @Test
    public void AddMultipleStationT() {
        n.addStation(s);
        Assert.assertEquals(n.getStationByName(s.getName()), s);
        Station s2;
        s2 = new Station("Station2", "toto", 2, 10.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(n.getLines(), hash_Set);
        Assert.assertEquals(n.getStationByName(s.getName()), s);

        Assert.assertEquals(n.getStationByName(s2.getName()), s2);

        hash_Set.add("test");
        Station s3;
        s3 = new Station("Station3", "test", 1, 0);
        n.addStation(s3);
        Assert.assertEquals(n.getLines(), hash_Set);
        Assert.assertEquals(n.getStationByName(s3.getName()), s3);

    }
    @Test
    public void RemoveLineExistingStation1(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2", "toto",2,10.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(n.getLines(),hash_Set);
        s2.removeLine("toto");
        Assert.assertEquals(n.getLines(),hash_Set);
    }
    @Test
    public void RemoveLineExistingStation2(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2", "toto2",1,0.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertEquals(n.getLines(),hash_Set);
        s2.removeLine("toto2");
        Assert.assertNotEquals(n.getLines(),hash_Set);
        hash_Set.remove("toto2");
        Assert.assertEquals(n.getLines(),hash_Set);
    }
    @Test
    public void ModifiedLineExistingStation(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2", "toto2",1,0.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertEquals(n.getLines(),hash_Set);
        s2.addLine("toto2",2,10.0);
        Assert.assertEquals(n.getLines(),hash_Set);
        hash_Set.remove("toto2");
        Assert.assertNotEquals(n.getLines(),hash_Set);
    }
    @Test
    public void AddMultipleStationWithdifferentLine(){
        n.addStation(s);
        Assert.assertEquals(n.getStationByName(s.getName()), s);
        Station s2;
        s2 = new Station("Station2", "toto2",2,10.0);
        s2.addLine("toto", 3, 0.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertEquals(n.getLines(),hash_Set);
        Assert.assertEquals(n.getStationByLineAndNumber("toto",1), s);
        Assert.assertEquals(n.getStationByLineAndNumber("toto",3), s2);
        Assert.assertEquals(n.getStationByLineAndNumber("toto2",2), s2);
    }
    @Test
    public void GetStationNull(){
        Assert.assertNull(n.getStationByName("ma Staion"));

    }
    @Test
    public void GetLinesNull(){
        Set<String> hash_Set = new HashSet<>();
        Assert.assertEquals(n.getLines(),hash_Set);
    }
    @Test
    public void GetLinesNumberNull(){
        Assert.assertNull(n.getStationByLineAndNumber("test",1));
    }


    /*
    TEST DE VALID
     */
    /*



    REREGARDER TOUT LES TEST SI LES LIGNES NE SONT PAS ISOLE
     */
    @Test
    public void MinimaValid(){
        n.addStation(s);
        Assert.assertTrue(n.isValid());
    }
    @Test
    public void MinimaNonValid(){
        Station s2;
        s2 = new Station("Zero");
        n.addStation(s2);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidWrongStart(){
        Station s2;
        s2 = new Station("Zero","deregle",1,1.0);
        n.addStation(s2);
        Assert.assertFalse(n.isValid());
    }
    /*
    rajouter test avec valeur proche de zero.
     */
    @Test
    public void NonValidWrongStart2(){
        Station s2;
        s2 = new Station("Zero","deregle",1,20.0);
        n.addStation(s2);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidNoStart(){
        Station s2;
        s2 = new Station("Zero","deregle",2,10.0);
        n.addStation(s2);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void ValidMultiStation(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Assert.assertTrue(n.isValid());
    }

    @Test
    public void ValidOneStationMultiLine(){
        s.addLine("toto2",1,0.0);
        s.addLine("toto3",1,0.0);
        n.addStation(s);

        Assert.assertTrue(n.isValid());
    }
    @Test
    public void ValidOneStationMultiLine2(){
        n.addStation(s);
        s.addLine("toto2",1,0.0);
        s.addLine("toto3",1,0.0);
        Assert.assertTrue(n.isValid());
    }
    @Test
    public void Valid2Line2Station(){
        /*Faux station isolé*/
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto2",1,0.0);
        n.addStation(s2);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertEquals(n.getLines(),hash_Set);
        Assert.assertTrue(n.isValid());
    }
    @Test
    public void NonValidMultiStationOneLine1(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",3,10.0);
        n.addStation(s2);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidMultiStationOneLine2(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Station s3;
        s3 = new Station("One","toto",3,30.0);
        n.addStation(s3);
        Station s4;
        s4 = new Station("Two","toto",4,40.0);
        n.addStation(s4);
        Station s5;
        s5 = new Station("Three","toto",6,50.0);
        n.addStation(s5);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidMultiStationOneLineDistance1(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Station s3;
        s3 = new Station("One","toto",3,9.0);
        n.addStation(s3);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidMultiStationOneLineDistanceEqual1(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Station s3;
        s3 = new Station("One","toto",3,10.0);
        n.addStation(s3);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidMultiStationOneLineDoublon(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Station s3;
        s3 = new Station("One","toto",2,20.0);
        n.addStation(s3);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void NonValidMultiStationOneLineDoublonAndDistance(){
        n.addStation(s);
        Station s2;
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Station s3;
        s3 = new Station("One","toto",2,80.0);
        n.addStation(s3);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void TrueMultiStationMultiLine(){
        n.addStation(s);
        Station s2;
        Assert.assertTrue(n.isValid());
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Assert.assertTrue(n.isValid());
        Station s3;
        s3 = new Station("One","toto2",1,0.0);
        n.addStation(s3);
        Assert.assertTrue(n.isValid());
        Station s4;
        s4 = new Station("two","toto2",2,11.7);
        n.addStation(s4);
        Assert.assertTrue(n.isValid());
        Station s5;
        s5 = new Station("tree","toto2",3,21.0);
        n.addStation(s5);
        Assert.assertTrue(n.isValid());
        Station s6;
        s6 = new Station("for","toto",3,11.7);
        n.addStation(s6);
        Assert.assertTrue(n.isValid());
        Station s7 = new Station("Terminus","toto",4,25.0);
        s7.addLine("toto2",4,32);
        n.addStation(s7);
        Assert.assertTrue(n.isValid());
    }
    @Test
    public void FalseMultiStationMultiLine() {
        n.addStation(s);
        Station s2;
        Assert.assertTrue(n.isValid());
        s2 = new Station("Zero", "toto", 2, 10.0);
        n.addStation(s2);
        Assert.assertTrue(n.isValid());
        Station s3;
        s3 = new Station("One", "toto2", 1, 0.0);
        n.addStation(s3);
        Assert.assertTrue(n.isValid());
        Station s4;
        s4 = new Station("two", "toto2", 2, 11.7);
        n.addStation(s4);
        Assert.assertTrue(n.isValid());
        Station s5;
        s5 = new Station("tree", "toto2", 3, 21.0);
        n.addStation(s5);
        Assert.assertTrue(n.isValid());
        Station s6;
        s6 = new Station("for", "toto", 3, 11.7);
        n.addStation(s6);
        Assert.assertFalse(n.isValid());
    }
    @Test
    public void FalseMultiStationMultiLine2(){
        n.addStation(s);
        Station s2;
        Assert.assertTrue(n.isValid());
        s2 = new Station("Zero","toto",2,10.0);
        n.addStation(s2);
        Assert.assertTrue(n.isValid());
        Station s3;
        s3 = new Station("One","toto2",1,0.0);
        n.addStation(s3);
        Assert.assertTrue(n.isValid());
        Station s4;
        s4 = new Station("two","toto2",2,11.7);
        n.addStation(s4);
        Assert.assertTrue(n.isValid());
        Station s5;
        s5 = new Station("tree","toto2",3,21.0);
        n.addStation(s5);
        Assert.assertTrue(n.isValid());
        Station s6;
        s6 = new Station("for","toto",3,11.7);
        n.addStation(s6);
        Assert.assertTrue(n.isValid());
        Station s7 = new Station("Terminus","toto",4,25.0);
        s7.addLine("toto2",4,32);
        n.addStation(s7);
        n.addStation(new Station("vide"));
        Assert.assertFalse(n.isValid());
    }
    /*
    TODO
    $Faire un test valide multi line
    $faux 1 station plusieur ligne certaine incorrecter
    Vrai 2 Station plusisieur ligne croiser
    Faux Station > 1 plusieur ligne
    Creer un test type reseau bus besancon

    etc
    etc
     */
    @Test
    public void DistanceNotIsValid() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertTrue(n.distance("ma Station","Station2") < 0 );
    }

    @Test
    public void DistanceParameter1EqualsNull() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertTrue(n.distance(null,"Station2") < 0 );
    }

    @Test
    public void DistanceParameter2EqualsNull() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertTrue(n.distance("ma Station",null) < 0 );
    }

    @Test
    public void DistanceStation1NotExist() {
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertTrue(n.distance("ma Station","Station2") < 0 );
    }

    @Test
    public void DistanceStation2NotExist() {
        n.addStation(s);
        Assert.assertTrue(n.distance("ma Station","Station2") < 0 );
    }

    @Test
    public void DistanceStation1EqualsStation2() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertEquals(0,n.distance("ma Station","ma Station"),0);
    }

    @Test
    public void DistanceAddLine() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        Station s3;
        //s3 = new Station("")
        n.addStation(s2);
        Assert.assertEquals(0,n.distance("ma Station","ma Station"),0);
    }
    @Test
    public void DistanceStationSameLine() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertEquals(1,n.distance("ma Station","Station2"),0);
    }
    @Test
    public void DistanceStationSameLineInverse() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertEquals(1,n.distance("Station2","ma Station"),0);
    }
    @Test
    public void DistanceStationSameLineTrim1() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertEquals(1,n.distance("           ma Station","Station2"),0);
    }
    @Test
    public void DistanceStationSameLineTrim2() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertEquals(1,n.distance("ma Station","          Station2"),0);
    }
    @Test
    public void DistanceStationSameLineTrim3() {
        n.addStation(s);
        Station s2;
        s2 = new Station("Station2","toto",2,1.0);
        n.addStation(s2);
        Assert.assertEquals(1,n.distance("    ma Station","          Station2      "),0);
    }
    @Test
    public void DistanceStationDifferentLines() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(2,n.distance("ma Station", "Station3"));

    }
    @Test
    public void DistanceStationDifferentLines2() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(2,n.distance("Station4", "Station6"));

    }
    @Test
    public void DistanceStationDifferentLines3() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(3,n.distance("ma Station", "Station4"));

    }
    @Test
    public void DistanceStationDifferentLines4() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(2,n.distance("Station4","Station6"));
    }
    @Test
    public void DistanceStationCrossingLine() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(5,n.distance("ma Station","Station6"));
    }
    @Test
    public void DistanceStationCrossingLine2pathShort() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        s6.addLine("toto",5,4);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(4,n.distance("ma Station","Station6"));
    }
    @Test
    public void DistanceStationCrossingLine2pathLong() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        s6.addLine("toto",5,50);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(5,n.distance("ma Station","Station6"));
    }
    @Test
    public void DistanceStationCrossingLine2pathShortInverse() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        s6.addLine("toto",5,4);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(4,n.distance("Station6","ma Station"));
    }
    @Test
    public void DistanceStationCrossingLine2pathLongInverse() {
        n.addStation(s);
        Station s2, s3, s4, s5,s6;
        s2 = new Station("Station2","toto",2,1.0);
        s3 = new Station("Station3","toto",3,2.0);
        s4 = new Station("Station4","toto2",1,0.0);
        s4.addLine("toto",4,3.0);
        s5 = new Station("Station5","toto2",2,1.0);
        s6 = new Station("Station6","toto2",3,2.0);
        s6.addLine("toto",5,50);
        n.addStation(s2);
        n.addStation(s3);
        n.addStation(s4);
        n.addStation(s5);
        n.addStation(s6);
        Assert.assertEquals(5,n.distance("Station6","ma Station"));
    }
}
