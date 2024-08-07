package fr.ufc.l3info.oprog.parser;

import fr.ufc.l3info.oprog.Network;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 *  Quelques tests pour le package parser.
 */
public class NetworkParserTest {

    /** Chemin vers les fichiers de test */
    final String path = "./target/classes/Data/";

    /** Instance singleton du parser de stations */
    final NetworkParser parser = NetworkParser.getInstance();

    @Test
    public void testTokenizer() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroOK.txt"));
        assertEquals(53, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
        assertEquals(1, tokens.get(0).getLigne());
        assertEquals(1, tokens.get(0).getColonne());
        assertEquals(16, tokens.get(tokens.size()-1).getLigne());
        assertEquals(15, tokens.get(tokens.size()-1).getColonne());
    }


    @Test
    public void testParserOK() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        assertTrue(n instanceof ASTListeLignes);
        assertEquals(3, n.getNumChildren());

        assertEquals(6, n.getChild(0).getNumChildren());
        assertEquals(4, n.getChild(1).getNumChildren());
        assertEquals(4, n.getChild(2).getNumChildren());

        for (ASTNode n1 : n) {
            assertTrue(n1 instanceof ASTLigne);
            for (ASTNode nn1 : n1) {
                if (!(nn1 instanceof ASTChaine)) {
                    assertTrue(nn1 instanceof ASTDeclaration);
                    assertTrue(nn1.getChild(0) instanceof ASTChaine);
                    assertTrue(nn1.getChild(1) instanceof ASTNombre);
                }
            }
        }
    }


    @Test
    public void testNetworkBuilder() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));
    }

    @Test(expected = NetworkParserException.class)
    public void testParserPointVirgule() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroPointVirgule.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserNoGuillemetStation() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroSansGuillemet.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserNoGuillemetLine() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroSansGuillemetLine.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserOneGuillemetStation() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1GuillemetStationFirst.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserOneGuillemetStation2() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1GuillemetStationSecond.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserOneGuillemetLine() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1GuillemetLineFirst.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserOneGuillemetLine2() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1GuillemetLineSecond.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserEmptyLine() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroLigneVide.txt"));
    }
    @Test//(expected = NetworkParserException.class)
    public void testParserEmptyFiles() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroEmpty.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserNoAccolade() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroSansAccolade.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParser1AccoladeFirst() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1AccoladeFirst.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParser1AccoladeSecond() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1AccoladeSecond.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserGlobalAccolade() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroGlobalAccolade.txt"));
    }

    @Test(expected = NetworkParserException.class)
    public void testParserWithout2dots() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroWithout2dots.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserMissingComma() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroMissingComa.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserMissingLineToken() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroMissingLineToken.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserMissingLineStatment() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroMissingLineStatment.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserMissingStationToken() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroMissingStationToken.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserMissingKMToken() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroMissingKMToken.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserUnexpectedChar() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroUnexpectedChar.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserDoubleVirgule() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroDoublevirgule.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserDoubleAccolade() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroDoubleAccolade.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserDoubleGuillemet() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroDoubleGuillemet.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserLineInLine() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroLineInLine.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserListLine() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroListLigne.txt"));
    }
    @Test(expected = NetworkParserException.class)
    public void testParserDoubleStatement() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroDoubleLigneStatement.txt"));
    }

    @Test(expected = NetworkParserException.class)
    public void testParserEmptyDeclaration() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroEmptyDeclaration.txt"));
    }

    @Test(expected = NetworkParserException.class)
    public void testParserDeclarationNotNumber() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroDeclarationNotNumber.txt"));
    }

    @Test
    public void testParserEmptyLineName() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroEmptyLineName.txt"));
    }

    @Test
    public void testParserEmptyLineNameSpace() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroEmptyLineNameSpace.txt"));
    }

    @Test
    public void testParserEmptyStationName() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroEmptyStationName.txt"));
    }

    @Test
    public void testParserEmptyStationNameSpace() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroEmptyStationNameSpace.txt"));
    }

    @Test
    public void testParserNegativeNumberDeclaration() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroWrongNumberValueNeg.txt"));
    }

    @Test
    public void testParserNotNumberCroissantDeclaration() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroWrongNumberValueCroissant.txt"));
    }

    @Test
    public void testParserUnreachable() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroUnreachableLine.txt"));
    }

    @Test
    public void testParser1Line() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metro1Line.txt"));
    }

    @Test(expected = NetworkParserException.class)
    public void testParserNoSpace() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroDoubleLigneStatement.txt"));
    }

    @Test(expected = NetworkParserException.class)
    public void testParserTokenNumberFalse() throws IOException, NetworkParserException {
        parser.parse(new File(path + "metroTokenNumberFalse.txt"));
    }



}