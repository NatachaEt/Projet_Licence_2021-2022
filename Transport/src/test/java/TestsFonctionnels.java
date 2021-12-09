import fr.ufc.l3info.oprog.*;
import fr.ufc.l3info.oprog.parser.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestsFonctionnels {

    /** Chemin vers les fichiers de test */
    final String path = "./target/classes/Data/";

    /** Instance singleton du parser de stations */
    final NetworkParser parser = NetworkParser.getInstance();
    TicketMachine machine;

    @Before
    public void decl(){
        machine = TicketMachine.getInstance();
    }

    @Test
    public void TestsFonctionnelsStationNoExixt() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        assertEquals(0,visitor.getErrors().size());
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));

        ITicket t = machine.buyTicket(false,30);
        assertTrue(t instanceof BaseTicket);

        Map<Double,Integer> p =  new HashMap<>();
        p.put(0.0,10);
        p.put(5.0,20);
        p.put(10.0,30);
        Barrier barrier0 = Barrier.build(net,"s0",p);
        Barrier barrier1 = Barrier.build(net,"s1",p);
        Barrier barrier2 = Barrier.build(net,"s2",p);
        Barrier barrier3 = Barrier.build(net,"s3",p);
        Barrier barrier4 = Barrier.build(net,"s4",p);
        Barrier barrier5 = Barrier.build(net,"s5",p);
        assertTrue(barrier0.enter(t));
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier4.exit(t));
        assertFalse(t.isValid());
    }

    @Test
    public void TestsFonctionnelsTicketChild() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        assertEquals(0,visitor.getErrors().size());
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));

        ITicket t = machine.buyTicket(true,15);
        assertTrue(t instanceof BaseTicket);

        Map<Double,Integer> p =  new HashMap<>();
        p.put(0.0,10);
        p.put(5.0,20);
        p.put(10.0,30);
        Barrier barrier0 = Barrier.build(net,"s0",p);
        Barrier barrier1 = Barrier.build(net,"s1",p);
        Barrier barrier2 = Barrier.build(net,"s2",p);
        Barrier barrier3 = Barrier.build(net,"s3",p);
        Barrier barrier4 = Barrier.build(net,"s4",p);
        Barrier barrier5 = Barrier.build(net,"s5",p);
        assertTrue(barrier0.enter(t));
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier4.exit(t));
        assertFalse(t.isValid());
    }

    @Test
    public void TestsFonctionnelsTicketAdjustChild() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        assertEquals(0,visitor.getErrors().size());
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));

        ITicket t = machine.buyTicket(true,5);
        assertTrue(t instanceof BaseTicket);
        assertNull(t.getEntryStation());

        Map<Double,Integer> p =  new HashMap<>();
        p.put(0.0,10);
        p.put(5.0,20);
        p.put(10.0,30);
        Barrier barrier0 = Barrier.build(net,"s0",p);
        Barrier barrier1 = Barrier.build(net,"s1",p);
        Barrier barrier2 = Barrier.build(net,"s2",p);
        Barrier barrier3 = Barrier.build(net,"s3",p);
        Barrier barrier4 = Barrier.build(net,"s4",p);
        Barrier barrier5 = Barrier.build(net,"s5",p);
        assertTrue(barrier0.enter(t));
        assertEquals(t.getEntryStation(),"s0");
        assertFalse(barrier4.exit(t));
        ITicket adjust_t = machine.adjustFare(t,10);
        assertTrue(barrier4.exit(adjust_t));
        assertFalse(adjust_t.isValid());
    }

    @Test
    public void TestsFonctionnelsTenIllimitedTripsTicketChild() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        assertEquals(0,visitor.getErrors().size());
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));

        ITicket t = machine.buyTicket(true);
        assertTrue(t instanceof TenIllimitedTripsTicket);
        assertNull(t.getEntryStation());
        Map<Double,Integer> p =  new HashMap<>();
        p.put(0.0,10);
        p.put(5.0,20);
        p.put(10.0,30);
        Barrier barrier0 = Barrier.build(net,"s0",p);
        Barrier barrier1 = Barrier.build(net,"s1",p);
        Barrier barrier2 = Barrier.build(net,"s2",p);
        Barrier barrier3 = Barrier.build(net,"s3",p);
        Barrier barrier4 = Barrier.build(net,"s4",p);
        Barrier barrier5 = Barrier.build(net,"s5",p);
        assertTrue(barrier0.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier5.exit(t));

        assertTrue(barrier5.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s5");
        assertTrue(barrier1.exit(t));

        assertTrue(barrier1.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s1");
        assertTrue(barrier2.exit(t));

        assertTrue(barrier2.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s2");
        assertTrue(barrier3.exit(t));

        assertTrue(barrier3.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s3");
        assertTrue(barrier4.exit(t));

        assertTrue(barrier0.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier0.exit(t));

        assertTrue(barrier3.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s3");
        assertTrue(barrier5.exit(t));

        assertTrue(barrier0.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier5.exit(t));

        assertTrue(barrier0.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier5.exit(t));

        assertTrue(t.isValid());
        assertTrue(barrier0.enter(t));
        assertTrue(t.isValid());
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier5.exit(t));
        assertFalse(barrier0.enter(t));
        assertFalse(t.isValid());
    }

    @Test
    public void TestsFonctionnelsTicket() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        assertEquals(0,visitor.getErrors().size());
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));

        ITicket t = machine.buyTicket(false,30);
        assertTrue(t instanceof BaseTicket);
        assertNull(t.getEntryStation());

        Map<Double,Integer> p =  new HashMap<>();
        p.put(0.0,10);
        p.put(5.0,20);
        p.put(10.0,30);
        Barrier barrier0 = Barrier.build(net,"s0",p);
        Barrier barrier1 = Barrier.build(net,"s1",p);
        Barrier barrier2 = Barrier.build(net,"s2",p);
        Barrier barrier3 = Barrier.build(net,"s3",p);
        Barrier barrier4 = Barrier.build(net,"s4",p);
        Barrier barrier5 = Barrier.build(net,"s5",p);
        assertNull(Barrier.build(net,"s6",p));
        assertTrue(barrier0.enter(t));
        assertEquals(t.getEntryStation(),"s0");
        assertTrue(barrier4.exit(t));
        assertFalse(t.isValid());
    }

}
