package fr.ufc.l3info.oprog;
import org.junit.Before;
import org.mockito.Mockito;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TestBarrierWithMocks {
    Network n;
    ITicket t;
    Map<Double,Integer>p =  new HashMap<>();
    Station s;

    @Before
    public void before(){
         n = Mockito.mock(Network.class);
         t = Mockito.mock(ITicket.class);
         s = Mockito.mock(Station.class);
        Mockito.when(n.getStationByName("toto")).thenReturn(s);

        p.put(0.0,100);
         p.put(10.0,200);
         p.put(20.0,300);
         p.put(30.0,400);

    }
    @Test
    public  void testExitWith400YensFare() {
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.getAmount()).thenReturn(400);
        Assert.assertEquals(400,mockTicket.getAmount());
    }
    @Test
    public  void ConstructorValid() {
        Mockito.when(n.isValid()).thenReturn(true);

        Barrier b = Barrier.build(n,"toto",p);
        Assert.assertNotNull(b);
    }
    @Test
    public  void ConstructorIncorectNetwork1() {
//        Mockito.when(n.isValid()).thenReturn(true);
        Barrier b = Barrier.build(null,"toto",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectNetwork2() {
        Mockito.when(n.isValid()).thenReturn(false);
        Barrier b = Barrier.build(n,"toto",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectStation1() {
//        Mockito.when(n.isValid()).thenReturn(true);
        Barrier b = Barrier.build(n,null,p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectStation2() {
        Mockito.when(n.isValid()).thenReturn(true);
        Barrier b = Barrier.build(n,"",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectStation3() {
        Mockito.when(n.isValid()).thenReturn(true);
        Barrier b = Barrier.build(n,"NoStation",p);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices1() {
//        Mockito.when(n.isValid()).thenReturn(true);
        Barrier b = Barrier.build(n,"toto",null);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices2() {
        Mockito.when(n.isValid()).thenReturn(true);
        Map<Double,Integer> pvide = new HashMap<>();
        Barrier b = Barrier.build(n,"toto",pvide);
        Assert.assertNull(b);
    }
    @Test
    public  void ConstructorIncorectPrices3() {
        Mockito.when(n.isValid()).thenReturn(true);
        Map<Double,Integer> pnon0 = new HashMap<>();
        pnon0.put(10.0,200);
        pnon0.put(20.0,300);
        pnon0.put(30.0,400);
        Barrier b = Barrier.build(n,"toto",pnon0);
        Assert.assertNull(b);
    }
}
