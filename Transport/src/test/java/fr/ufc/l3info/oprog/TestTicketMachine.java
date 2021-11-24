package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTicketMachine {
    ITicket t;
    ITicket base;
    ITicket adjusted;
    @Before
    public void decl(){
        base = buyTicket(false,100);
    }

    @Test
    public void buyTicketBaseTicket(){
        t = buyTicket(true,10);
        boolean bol = false;
        if (t instanceof BaseTicket) bol = true;
        Assert.assertTrue(bol);
        Assert.assertEquals(10,t.getAmount());
        Assert.assertTrue(t.isChild());
        Assert.assertNull(t.getEntryStation());
        Assert.assertTrue(t.isValid());
    }

    @Test
    public void buyTicketTenIllimitedTripsTicket(){
        t = buyTicket(true);
        boolean bol = false;
        if (t instanceof BaseTicket) bol = true;
        Assert.assertTrue(bol);
        Assert.assertEquals(2147483647,t.getAmount());
        Assert.assertTrue(t.isChild());
        Assert.assertNull(t.getEntryStation());
        Assert.assertTrue(t.isValid());
    }

    @Test
    public void buyTicketNull(){
        t = buyTicket(true,-600);
        Assert.assertNull(t);
    }

    @Test
    public void adjustFareAjoutBaseTicket(){
        adjusted = adjustFare(base,100);
        boolean bol = false;
        if (t instanceof AdjustedTicket) bol = true;
        Assert.assertTrue(bol);
        Assert.assertEquals(200, adjusted.getAmount());
    }

    @Test
    public void adjustFareNull(){
        adjusted = adjustFare(null,100);
        Assert.assertNull(adjusted);
    }

    //TODO : Finir les tests adjustedFare somme ajout 0 ou inf et ticket non ajustable
    @Test
    public void adjustFareNoSuperieur0(){

    }


}
