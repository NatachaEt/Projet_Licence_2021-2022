package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Test;

public class TestTicketMachine {
    ITicket t;

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

}
