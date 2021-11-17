package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestBarrierIntegration {
Network n;
ITicket t;
Station s;Station s2;Station s3;Station s4;Station s5;Station s6;Station s7;Station s8;
Map<Double,Integer> p =  new HashMap<>();

    @Before
    public void init(){
        n = new Network();

        s = new Station("Base_Station_green", "green",1,0.0);
        s2 = new Station("Station_green2", "green",2,10.0);
        s3 = new Station("Station_green3", "green",3,20.0);
        s4 = new Station("Station_green4", "green",4,30.0);
        s5 = new Station("Base_Station_red", "red",1,0.0);
        s6 = new Station("Station_red2", "red",2,5.0);
        s7 = new Station("Base_Station_yellow", "yellow",1,0.0);
        s8 = new Station("Terminus", "yellow",2,5.0);
        s8.addLine("green",5,35.0);
        s8.addLine("red",3,35.0);

        n.addStation(s);n.addStation(s2);n.addStation(s3);n.addStation(s4);
        n.addStation(s5);n.addStation(s6);n.addStation(s7);n.addStation(s8);
        Assert.assertTrue(n.isValid());

        p.put(0.0,100);
        p.put(10.0,200);
        p.put(20.0,300);
        p.put(30.0,400);
        t = new BaseTicket(false,400);
    }
    @Test
    public void test(){

    }
    @Test
    public  void ConstructorValid() {

        Barrier b = Barrier.build(n,"Terminus",p);
        Assert.assertNotNull(b);
    }
    @Test
    public  void ConstructorIncorectNetwork1() {
        Barrier b = Barrier.build(null,"Base_Station_green",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectNetwork2() {
        Network nf = new Network();
        nf.addStation(s);
        nf.addStation(s3);
        Assert.assertFalse(nf.isValid());
        Barrier b = Barrier.build(nf,"Base_Station_red",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectStation1() {
        Barrier b = Barrier.build(n,null,p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectStation2() {
        Barrier b = Barrier.build(n,"",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectStation3() {
        Barrier b = Barrier.build(n,"NoStation",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices1() {
        Barrier b = Barrier.build(n,"Base_Station_yellow",null);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices2() {
        Map<Double,Integer> pvide = new HashMap<>();
        Barrier b = Barrier.build(n,"Station_green4",pvide);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices3() {
        Map<Double,Integer> pnon0 = new HashMap<>();
        pnon0.put(10.0,200);
        pnon0.put(20.0,300);
        pnon0.put(30.0,400);
        Barrier b = Barrier.build(n,"Station_green2",pnon0);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices4() {
        Map<Double,Integer> pinf0 = new HashMap<>();
        pinf0.put(0.0,200);
        pinf0.put(10.0,300);
        pinf0.put(-30.0,400);
        Barrier b = Barrier.build(n,"Station_green2",pinf0);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices4bis() {
        Map<Double,Integer> pinf0 = new HashMap<>();
        pinf0.put(0.0,200);
        pinf0.put(10.0,300);
        pinf0.put(30.0,-400);
        Barrier b = Barrier.build(n,"Station_green2",pinf0);
        Assert.assertNull(b);
    }
    @Test
    public void EnteringOK(){
        Barrier b = Barrier.build(n,"Base_Station_green",p);
        Assert.assertNotNull(b);
        Assert.assertTrue(b.enter(t));
        Assert.assertTrue(t.isValid());
        Assert.assertEquals(t.getEntryStation(),"Base_Station_green");
    }
    @Test
    public void EnteringFalseAmount(){
        Barrier b = Barrier.build(n,"Base_Station_green",p);
        Assert.assertNotNull(b);
        ITicket tp = new BaseTicket(false,0);
        Assert.assertFalse(b.enter(tp));
        Assert.assertTrue(tp.isValid());
        Assert.assertNotEquals(tp.getEntryStation(),"Base_Station_green");
    }
    @Test
    public void EnteringFalseAmount2(){
        Barrier b = Barrier.build(n,"Base_Station_green",p);
        Assert.assertNotNull(b);
        ITicket tp = new BaseTicket(false,-10);
        Assert.assertFalse(b.enter(tp));
        Assert.assertTrue(tp.isValid());
        Assert.assertNotEquals(tp.getEntryStation(),"Base_Station_green");
    }
    @Test
    public void EnteringFalseEntered(){
        Barrier b = Barrier.build(n,"Base_Station_green",p);
        Barrier b2 = Barrier.build(n,"Terminus",p);
        Assert.assertNotNull(b);
        Assert.assertNotNull(b2);
        Assert.assertTrue(b.enter(t));
        Assert.assertTrue(t.isValid());
        Assert.assertEquals(t.getEntryStation(),"Base_Station_green");

        Assert.assertFalse(b2.enter(t));
        Assert.assertFalse(t.isValid());
       // Assert.assertNotEquals(t.getEntryStation(),"Terminus");
        Assert.assertNull(t.getEntryStation());
        //Assert.assertNotEquals(t.getEntryStation(),"Base_Station_green");
    }
    @Test
    public void EnteringFalseEnteredSame(){
        Barrier b = Barrier.build(n,"Base_Station_green",p);
        Assert.assertNotNull(b);
        Assert.assertTrue(b.enter(t));
        Assert.assertTrue(t.isValid());
        Assert.assertEquals(t.getEntryStation(),"Base_Station_green");

        Assert.assertFalse(b.enter(t));
        Assert.assertFalse(t.isValid());
       // Assert.assertNotEquals(t.getEntryStation(),"Base_Station_green");
        Assert.assertNull(t.getEntryStation());
    }
    @Test
    public void EnteringFalseInvalid(){
        Barrier b = Barrier.build(n,"Base_Station_green",p);
        Assert.assertNotNull(b);
        t.invalidate();
        Assert.assertFalse(t.isValid());
        Assert.assertFalse(b.enter(t));
        Assert.assertFalse(t.isValid());
       // Assert.assertEquals(t.getEntryStation(),"Base_Station_green");
        Assert.assertNull(t.getEntryStation());
    }
    @Test
    public void ExitSucces(){


            Barrier b2 = Barrier.build(n,"Base_Station_green",p);
            Barrier b = Barrier.build(n,"Terminus",p);
            ITicket tp = new BaseTicket(false,400);
            Assert.assertTrue(b2.enter(tp));
            Assert.assertTrue(tp.isValid());
            Assert.assertEquals("Base_Station_green",tp.getEntryStation());
            Assert.assertTrue(b.exit(tp));
            Assert.assertFalse(tp.isValid());
    }
    @Test
    public void ExitSuccesGreaterAmount(){

        Barrier b2 = Barrier.build(n,"Base_Station_green",p);
        Barrier b = Barrier.build(n,"Station_green3",p);
        ITicket tp = new BaseTicket(false,700);
        Assert.assertTrue(b2.enter(tp));
        Assert.assertTrue(tp.isValid());
        Assert.assertEquals("Base_Station_green",tp.getEntryStation());
        Assert.assertTrue(b.exit(tp));
        Assert.assertFalse(tp.isValid());
    }
    @Test
    public void ExitNonValid(){
        Barrier b = Barrier.build(n,"Terminus",p);
        t.invalidate();
        Assert.assertFalse(b.exit(t));
        Assert.assertFalse(t.isValid());
    }
    @Test
    public void ExitNotEntered(){
        Barrier b = Barrier.build(n,"Terminus",p);
        Assert.assertFalse(b.exit(t));
        Assert.assertTrue(t.isValid());
        Assert.assertNull(t.getEntryStation());
    }
    @Test
    public void ExitIncorrectAmount(){

        Barrier b2 = Barrier.build(n,"Base_Station_green",p);
        Barrier b = Barrier.build(n,"Terminus",p);
        ITicket tp = new BaseTicket(false,100);
        Assert.assertTrue(b2.enter(tp));
        Assert.assertTrue(tp.isValid());


        Assert.assertEquals("Base_Station_green",tp.getEntryStation());
        Assert.assertEquals(35,n.distance("Base_Station_green","Terminus"),0);
        Assert.assertFalse(b.exit(tp));
        Assert.assertTrue(tp.isValid());
        /**
         * DEvrait etre equal Base_Station_green ?
         *         Assert.assertNull(tp.getEntryStation());
          */

    }
    @Test
    public void ExitCorrectAmountChild(){

        Barrier b2 = Barrier.build(n,"Base_Station_green",p);
        Barrier b = Barrier.build(n,"Terminus",p);
        ITicket tp = new BaseTicket(true,200);
        Assert.assertTrue(b2.enter(tp));
        Assert.assertTrue(tp.isValid());
        Assert.assertEquals("Base_Station_green",tp.getEntryStation());
        Assert.assertTrue(b.exit(tp));
        Assert.assertFalse(tp.isValid());

    }
    @Test
    public void ExitIncorrectAmountChild(){

        Barrier b2 = Barrier.build(n,"Base_Station_green",p);
        Barrier b = Barrier.build(n,"Terminus",p);
        ITicket tp = new BaseTicket(true,190);
        Assert.assertTrue(b2.enter(tp));
        Assert.assertTrue(tp.isValid());
        Assert.assertEquals("Base_Station_green",tp.getEntryStation());
        Assert.assertFalse(b.exit(tp));
        Assert.assertTrue(tp.isValid());

    }
    @Test
    public void ExitFailedThenSucces(){

        Barrier b2 = Barrier.build(n,"Base_Station_green",p);
        Barrier b = Barrier.build(n,"Station_green2",p);
        ITicket tp = new BaseTicket(false,150);
        Assert.assertTrue(b2.enter(tp));
        Assert.assertTrue(tp.isValid());


        Assert.assertEquals("Base_Station_green",tp.getEntryStation());

        Assert.assertFalse(b.exit(tp));
        Assert.assertTrue(tp.isValid());

        Assert.assertEquals(tp.getEntryStation(),"Base_Station_green");

        ITicket adj = new AdjustedTicket(tp,200);

        Assert.assertTrue(b.exit(adj));
        //Assert.assertFalse(adj.isValid());
    }


}
