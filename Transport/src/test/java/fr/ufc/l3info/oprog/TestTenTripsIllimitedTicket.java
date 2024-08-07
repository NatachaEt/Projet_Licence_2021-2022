package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTenTripsIllimitedTicket {
    ITicket ticket10child;
    ITicket ticket10adult;
    @Before
    public void decl(){
        ticket10child = new TenIllimitedTripsTicket(true);
        ticket10adult = new TenIllimitedTripsTicket (false);
    }

    @Test
    public void CorrectChild(){
        Assert.assertTrue(ticket10child.isChild());
    }

    @Test
    public void CorrectAdult(){
        Assert.assertFalse(ticket10adult.isChild());
    }

    @Test
    public void CorrectChildAmount(){
        Assert.assertEquals(2147483647,ticket10child.getAmount());
    }

    @Test
    public void testEnteringNameStationNullAdult(){ Assert.assertFalse(ticket10adult.entering(null));}

    @Test
    public void testEnteringNameStationNullChild(){ Assert.assertFalse(ticket10child.entering(null));}

    @Test
    public void testEnteringNameVoidAdult(){ Assert.assertFalse(ticket10adult.entering(""));}

    @Test
    public void testEnteringNameVoidChild(){ Assert.assertFalse(ticket10child.entering(""));}

    @Test
    public void testEnteringNameEmptyAdult(){ Assert.assertFalse(ticket10adult.entering("     "));}

    @Test
    public void testEnteringNameEmptyChild(){ Assert.assertFalse(ticket10child.entering("     "));}

    @Test public void testEnteringAdult(){ Assert.assertTrue(ticket10adult.entering("Station1"));}

    @Test public void testEnteringChild(){ Assert.assertTrue(ticket10child.entering("Station1"));}

    @Test
    public void testEnteringInvalidateTicketAdult(){
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.entering("Station1"));
    }

    @Test
    public void testEnteringInvalidateTicketChild(){
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.entering("Station1"));
    }

    @Test
    public void testEnteringNameEmptyEnteringAdult(){
        ticket10adult.entering("");
        Assert.assertTrue(ticket10adult.entering("Station1"));
    }

    @Test
    public void testEnteringNameEmptyEnteringChild(){
        ticket10adult.entering("");
        Assert.assertTrue(ticket10adult.entering("Station1"));
    }

    @Test
    public void testInvalidateAdult(){
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
    }

    @Test
    public void testInvalidateChild(){
        ticket10child.invalidate();
        Assert.assertTrue(ticket10child.isValid());
    }

    @Test
    public void testInvalidate2Adult(){
        ticket10adult.entering("");
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
    }

    @Test
    public void testInvalidate2Child(){
        ticket10adult.entering("");
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
    }

    @Test
    public void testInvalidate10Adult(){
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
        ticket10adult.invalidate();
        Assert.assertFalse(ticket10adult.isValid());
    }

    @Test
    public void testGetEntryStationNoEntering(){ Assert.assertNull(ticket10adult.getEntryStation());}

    @Test
    public void testGetEntryStationNoEnteringTicketValid(){
        ticket10adult.getEntryStation();
        Assert.assertTrue(ticket10adult.isValid());
    }

    @Test
    public void testGetEntryStationEntering(){
        ticket10child.entering("Station");
        Assert.assertEquals("Station",ticket10child.getEntryStation());
    }

    @Test
    public void testGetEntryStationInvalidate(){
        ticket10child.invalidate();
        Assert.assertNull(ticket10child.getEntryStation());
    }

    @Test
    public void testIsValidCreate(){
        Assert.assertTrue(ticket10adult.isValid());
    }

    @Test
    public void testIsValidEntering(){
        ticket10child.entering("Station");
        Assert.assertTrue(ticket10child.isValid());
    }

    @Test
    public void testIsValidBadEntering(){
        ticket10child.entering("");
        Assert.assertTrue(ticket10child.isValid());
    }

    @Test
    public void testIsValidBadEnteringNull(){
        ticket10adult.entering(null);
        Assert.assertTrue(ticket10adult.isValid());
    }

    @Test
    public void testIsValidInvalidate(){
        ticket10adult.invalidate();
        Assert.assertTrue(ticket10adult.isValid());
    }

}
