package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTicketMachine {
    ITicket t;
    ITicket base;
    ITicket adjusted;
    TicketMachine machine;
    @Before
    public void decl(){
        machine = TicketMachine.getInstance();
        base = machine.buyTicket(false,100);
    }

    @Test
    public void buyTicketBaseTicket(){
        t = machine.buyTicket(true,10);
        boolean bol = t instanceof BaseTicket;
        Assert.assertTrue(bol);
        Assert.assertEquals(10,t.getAmount());
        Assert.assertTrue(t.isChild());
        Assert.assertNull(t.getEntryStation());
        Assert.assertTrue(t.isValid());
    }

    @Test
    public void buyTicketTenIllimitedTripsTicket(){
        t = machine.buyTicket(true);
        boolean bol = t instanceof TenIllimitedTripsTicket;
        Assert.assertTrue(bol);
        Assert.assertEquals(2147483647,t.getAmount());
        Assert.assertTrue(t.isChild());
        Assert.assertNull(t.getEntryStation());
        Assert.assertTrue(t.isValid());
    }

    @Test
    public void buyTicketNull(){
        t = machine.buyTicket(true,-600);
        Assert.assertNull(t);
    }

    @Test
    public void adjustFareAjoutBaseTicket(){
        adjusted = machine.adjustFare(base,100);
        boolean bol = adjusted instanceof AdjustedTicket;
        Assert.assertTrue(bol);
        Assert.assertEquals(200, adjusted.getAmount());
    }

    @Test
    public void adjustFareNull(){
        adjusted = machine.adjustFare(null,100);
        Assert.assertNull(adjusted);
    }

    //TODO : Finir les tests adjustedFare somme ajout 0 ou inf et ticket non ajustable
    @Test
    public void adjustFareNoSuperieur0(){
        adjusted = machine.adjustFare(base,0);
        Assert.assertEquals(base.getClass(),adjusted.getClass());
        Assert.assertEquals(base.isChild(), adjusted.isChild());
        Assert.assertEquals(base.getEntryStation(),adjusted.getEntryStation());
        Assert.assertEquals(base.getAmount(),adjusted.getAmount());
        Assert.assertEquals(base.isValid(),adjusted.isValid());
    }

    public void adjustFareNoSNeg(){
        adjusted = machine.adjustFare(base,-5);
        Assert.assertEquals(base.getClass(),adjusted.getClass());
        Assert.assertEquals(base.isChild(), adjusted.isChild());
        Assert.assertEquals(base.getEntryStation(),adjusted.getEntryStation());
        Assert.assertEquals(base.getAmount(),adjusted.getAmount());
        Assert.assertEquals(base.isValid(),adjusted.isValid());
    }

    public void adjustFareNoSAdjust(){
        t = new TenIllimitedTripsTicket(true);
        adjusted = machine.adjustFare(t,-5);
        boolean bol = t instanceof TenIllimitedTripsTicket;
        Assert.assertTrue(bol);
    }


}
