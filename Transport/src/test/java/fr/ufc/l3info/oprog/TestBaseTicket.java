package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
JE DOIS M'OCCUPER DE BASE TICKET 01
 **/

/**Renommez, dans votre fichier de tests les méthodes de test qui échouent sur
        l'implem que vous avez testée en leur ajoutant le suffixe _KO.* */
public class TestBaseTicket {
    ITicket e;
    ITicket a;
    @Before
    public void decl(){
         e = new BaseTicket(true,10);
         a = new BaseTicket(false,22);
    }

    @Test
    public void CorrectChild(){
        Assert.assertTrue(e.isChild());
    }
    @Test
    public void CorrectAdult(){
        Assert.assertFalse(a.isChild());
    }
    @Test
    public void IncorrectConsChild(){
        ITicket ef = new BaseTicket(true,-20);
        Assert.assertTrue(e.isChild());
    }
    @Test
    public void IncorrectConsAdult(){
        ITicket af = new BaseTicket(false,-2);
        Assert.assertFalse(a.isChild());

    }
    @Test
    public void CorrectChildAmount(){
        Assert.assertEquals(e.getAmount(),10);
    }
    @Test
    public void CorrectAdultAmount(){
        ITicket a2 = new BaseTicket(false,1032);
        Assert.assertEquals(a2.getAmount(),1032);
    }
    @Test
    public void IncorrectConsChildAmount_KO(){
        /**
         * Le montant du ticket est censé etre nul(egal a 0, un int ne peut etre null en java)
         * car le montant passé au constructeur est negatif
         */
        ITicket e = new BaseTicket(true,-20);
        Assert.assertEquals(0,e.getAmount());

    }
    @Test
    public void IncorrectConsAdultAmount_KO(){
        /**
         * Le montant du ticket est censé etre nul(egal a 0, un int ne peut etre null en java)
         * car le montant passé au constructeur est negatif
         */
        ITicket a = new BaseTicket(false,-2);
        Assert.assertEquals(0,a.getAmount());
    }

    /**
     * Repeter les test en haut pour ticker entered et invalid
     */

    @Test
    public void EnteringIssued(){
        Assert.assertTrue(a.entering("Station"));
        Assert.assertTrue(a.isValid());
    }
    @Test
    public void EnteringIssuedEmpty(){
        Assert.assertFalse(a.entering(""));
        Assert.assertFalse(a.isValid());
    }
    @Test
    public void EnteringIssuedNull(){
        Assert.assertFalse(a.entering(null));
        Assert.assertFalse(a.isValid());
    }
    @Test
    public void EnteringChildIssued(){
        Assert.assertTrue(e.entering("Station"));
        Assert.assertTrue(a.isValid());

    }
    @Test
    public void EnteringChildIssuedEmpty(){
        Assert.assertFalse(e.entering(""));
        Assert.assertFalse(e.isValid());
    }
    @Test
    public void EnteringChildIssuedNull(){
        Assert.assertFalse(e.entering(null));
        Assert.assertFalse(e.isValid());
    }
    /**
     * Peut etre tester pour des ticket non correct
     */
    @Test
    public void DoubleEnteringIssued(){
        Assert.assertTrue(a.entering("Station"));
        Assert.assertTrue(a.isValid());

        Assert.assertFalse(a.entering("Station"));
        Assert.assertFalse(a.isValid());
    }
    @Test
    public void DoubleEnteringIssuedEmpty(){
        Assert.assertFalse(a.entering(""));
        Assert.assertFalse(a.isValid());
        Assert.assertFalse(a.entering(""));
        Assert.assertFalse(a.isValid());


    }
    @Test
    public void DoubleEnteringIssuedNull(){
        Assert.assertFalse(a.entering(null));
        Assert.assertFalse(a.isValid());

        Assert.assertFalse(a.entering(null));

        Assert.assertFalse(a.isValid());

    }
    @Test
    public void DoubleEnteringChildIssued(){
        Assert.assertTrue(e.entering("Station"));
        Assert.assertTrue(e.isValid());

        Assert.assertFalse(e.entering("Station"));
        Assert.assertFalse(e.isValid());

    }

    @Test
    public void IssueInvalidate(){
        a.invalidate();
        Assert.assertFalse(a.isValid());
    }
    @Test
    public void EnteredInvalidate(){
        Assert.assertTrue(a.entering("Station"));
        a.invalidate();
        Assert.assertFalse(a.isValid());
    }
    @Test
    public void InvalidInvalidate(){
        a.invalidate();
        Assert.assertFalse(a.isValid());
        a.invalidate();
        Assert.assertFalse(a.isValid());

    }
    @Test
    public void GetEntryIssued(){
        Assert.assertNull(a.getEntryStation());
    }
    @Test
    public void GetEntryEntered(){
        Assert.assertTrue(a.entering("Station"));
        Assert.assertEquals("Station",a.getEntryStation());
    }
    @Test
    public void GetEntryEnteredNonName(){
        Assert.assertFalse(a.entering(""));
        Assert.assertNull(a.getEntryStation());
    }
    @Test
    public void GetEntryEnteredNull(){
        Assert.assertFalse(a.entering(null));
        Assert.assertNull(a.getEntryStation());
    }
    @Test
    public void GetEntryInvalidate(){
        a.invalidate();
        Assert.assertNull(a.getEntryStation());
    }
    @Test
    public void IsValid(){
        Assert.assertTrue(a.isValid());
    }

}
