package fr.ufc.l3info.oprog;

import org.junit.Assert;
//import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


/**
 * Test file for the Station class.
 */
public class TestStation {

    Station s;

    @Test   // indicates that this method is a test case
    public void testName() {
        // creation of an object to test
        s = new Station("ma Station");
        Assert.assertNotNull(s);
        Assert.assertEquals("ma Station", s.getName());
    }
    @Test
    public void Constructor2(){
        s = new Station("ma Station", "toto",1,10.0);
        // an observation that will obviously succeed
        Assert.assertNotNull(s);
        Assert.assertEquals("ma Station", s.getName());

        //verification list de station
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(hash_Set, s.getLines());
        //verification du numero de la ligne
        Assert.assertEquals(1, s.getNumberForLine("toto"));
        //verification distance de la ligne
        Assert.assertEquals(10.0, s.getDistanceForLine("toto"), 0.0);
    }
    @Test
    public void ConstructorTrim(){
        s = new Station("     ma Station       ", "        toto    ",1,10.0);
        // an observation that will obviously succeed
        Assert.assertNotNull(s);
        Assert.assertEquals("ma Station", s.getName());

        //verification list de station
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(hash_Set, s.getLines());
        //verification du numero de la ligne
        Assert.assertEquals(1, s.getNumberForLine("         toto"));
        //verification distance de la ligne
        Assert.assertEquals(10.0, s.getDistanceForLine("         toto"), 0.0);
    }
    @Test
    public void Constructor2False(){
        //s = new Station(null, "toto",1,10.0);
        // an observation that will obviously succeed
        //Assert.assertNull(s);
        Station s1;
        Station s2;
        Station s3;
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");

        s1 = new Station("ma_station", null,1,10.0);
        Assert.assertNotNull(s1);
        Assert.assertNotEquals(hash_Set, s1.getLines());

        s2 = new Station("ma_station", "toto",-1,10.0);
        Assert.assertNotNull(s2);
        Assert.assertNotEquals(hash_Set, s2.getLines());
        Assert.assertEquals(0, s2.getNumberForLine("toto"));
        //verification distance de la ligne
        Assert.assertEquals(-1.0, s2.getDistanceForLine("toto"), 0.0);
        Assert.assertTrue(s2.getDistanceForLine("toto") < 0.0);


        s3 = new Station("ma_station", "toto",1,-10.0);
        Assert.assertNotNull(s3);
        Assert.assertNotEquals(hash_Set, s3.getLines());
        Assert.assertEquals(0, s3.getNumberForLine("toto"));
        //verification distance de la ligne
        Assert.assertTrue(s3.getDistanceForLine("toto") < 0.0);

    }
    @Test
    public void AddLine(){
        s = new Station("ma Station", "toto",1,10.0);
        // an observation that will obviously succeed
        Assert.assertNotNull(s);
        //verification list de station
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(hash_Set, s.getLines());

        s.addLine("toto2",2,0.0);
        hash_Set.add("toto2");
        Assert.assertEquals(hash_Set, s.getLines());
    }
    @Test
    public void AddLinefalse(){
        s = new Station("ma Station", "toto",1,10.0);
        // an observation that will obviously succeed
        Assert.assertNotNull(s);
        //verification list de station
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(hash_Set, s.getLines());

        s.addLine(null,2,15.0);
        hash_Set.add("toto2");
        Assert.assertNotEquals(hash_Set, s.getLines());

        s.addLine("toto2",-1,15.0);
        Assert.assertNotEquals(hash_Set, s.getLines());

        s.addLine("toto2",1,-2);
        Assert.assertNotEquals(hash_Set, s.getLines());

        Set<String> hash_Set2 = new HashSet<>();
        hash_Set2.add("");
        s.addLine("",12,15.0);
        Assert.assertNotEquals(hash_Set2, s.getLines());

    }
    @Test
    public void AddLineExisting(){
        s = new Station("ma Station", "toto",1,10.0);
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");
        Assert.assertEquals(hash_Set, s.getLines());

        s.addLine("toto",2,20.0);
        Assert.assertEquals(hash_Set, s.getLines());
        Assert.assertEquals(2, s.getNumberForLine("toto"));
        Assert.assertEquals(20.0, s.getDistanceForLine("toto"), 0.0);
    }
    @Test
    public void AddLineCons1(){
        s = new Station("ma Station");
        Set<String> hash_Set = new HashSet<>();
        hash_Set.add("toto");

        s.addLine("toto",2,20.0);
        Assert.assertEquals(hash_Set, s.getLines());
        Assert.assertEquals(2, s.getNumberForLine("toto"));
        Assert.assertEquals(20.0, s.getDistanceForLine("toto"), 0.0);
    }
    @Test
    public void RemoveExistingLine(){
        s = new Station("ma Station", "toto",1,10.0);
        Set<String> hash_Set = new HashSet<>();
        s.addLine("toto2",2,20.0);
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertEquals(hash_Set, s.getLines());
        s.removeLine("toto");
        hash_Set.remove("toto");
        Assert.assertEquals(hash_Set, s.getLines());
    }
    @Test
    public void RemoveNonExistingLine(){
        s = new Station("ma Station", "toto",1,10.0);
        Set<String> hash_Set = new HashSet<>();
        s.addLine("toto2",2,20.0);
        hash_Set.add("toto");
        hash_Set.add("toto2");
        Assert.assertEquals(hash_Set, s.getLines());
        s.removeLine("toto4");
        Assert.assertEquals(hash_Set, s.getLines());
        s.removeLine(null);
        Assert.assertEquals(hash_Set, s.getLines());


    }

    @Test
    public void Equals(){
        s = new Station("ma Station", "toto",1,10.0);
        Station o;
        o = new Station("ma Station", "toto2",2,20.0);
        Assert.assertTrue(s.equals(o));
    }
    @Test
    public void NonEquals(){
        s = new Station("ma Station", "toto",1,10.0);
        Station o;
        o = new Station("ma_Station", "toto2",2,20.0);
        Assert.assertFalse(s.equals(o));
    }
    @Test
    public void EqualstoSelf(){
        s = new Station("ma Station", "toto",1,10.0);
        Assert.assertTrue(s.equals(s));
    }
    @Test
    public void EqualsNull(){
        s = new Station("ma Station", "toto",1,10.0);
        Assert.assertFalse(s.equals(null));
    }
    @Test
    public void EqualsObject(){
        s = new Station("ma Station", "toto",1,10.0);
        Assert.assertFalse(s.equals(new HashSet<>()));
    }
    @Test
    public void EqualsHashCode(){
        s = new Station("ma Station", "toto",1,10.0);
        Station s2;
        s2 = new Station("    ma Station", "atoto4",3,10.0);
        Station s3;
        s3 = new Station("ma Station", "btoto3",1,54.0);
        /*
        Toutes ces hash code sont censé etre identique car les staion sont considéré etre identique si elle posendent
        le meme nom car les nom sont unique
         */
        Assert.assertEquals(s.hashCode(), s2.hashCode());
        Assert.assertEquals(s.hashCode(), s3.hashCode());
        Assert.assertEquals(s2.hashCode(), s3.hashCode());

    }
    @Test
    public void NotEqualsHashCode(){
        s = new Station("ma Station", "toto",1,10.0);
        Station s2;
        s2 = new Station("Station", "atoto4",1,10.0);
        Station s3;
        s3 = new Station("Gare viotte", "btoto3",1,54.0);

        Assert.assertNotEquals(s.hashCode(), s2.hashCode());
        Assert.assertNotEquals(s.hashCode(), s3.hashCode());
        Assert.assertNotEquals(s2.hashCode(), s3.hashCode());

    }



}