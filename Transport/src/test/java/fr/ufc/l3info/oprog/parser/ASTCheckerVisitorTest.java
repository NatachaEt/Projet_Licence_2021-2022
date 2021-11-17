package fr.ufc.l3info.oprog.parser;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ASTCheckerVisitorTest {
    /** Chemin vers les fichiers de test */
    final String path = "./target/classes/Data/";

    /** Instance singleton du parser de stations */
    final NetworkParser parser = NetworkParser.getInstance();

    @Test
    public void testCheckerEmpty() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroEmpty.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.EMPTY_LINE_LIST;
        assertEquals(1,kIND.size());
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0],value);
        }
    }

    @Test
    public void testCheckerDuplicateLineName() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroDuplicateLineName.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(1,kIND.size());
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.DUPLICATE_LINE_NAME;
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0],value);
        }
    }

    @Test
    public void testCheckerEmptyLineName() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroEmptyLineName.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(1,kIND.size());
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.EMPTY_LINE_NAME;
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0], value);
        }
    }

    @Test
    public void testCheckerEmptyLineNameSpace() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroEmptyLineNameSpace.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(1,kIND.size());
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.EMPTY_LINE_NAME;
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0], value);
        }
    }

    @Test
    public void testCheckerDuplicateStationName() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroDuplicateStationName.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(1,kIND.size());
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.DUPLICATE_STATION_NAME;
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            boolean bol = false;
            for(int i = 0; i < 1; i++){ if(value == errors[i]) bol = true;}
            assertTrue(bol);
        }
    }

    @Test
    public void testCheckerEmptyStationName() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroEmptyStationName.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> Kind = visitor.getErrors();
        assertEquals(1,Kind.size());
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.EMPTY_STATION_NAME;
        for (Map.Entry<String, ERROR_KIND> entry : Kind.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0], value);
        }
    }

    @Test
    public void testASTCheckerVisitorEmptyStationNameSpace() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroEmptyStationNameSpace.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.EMPTY_STATION_NAME;
        assertEquals(1,kIND.size());
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0], value);
        }
    }

    @Test
    public void testCheckerWrongNumberValueNeg() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroWrongNumberValueNeg.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.WRONG_NUMBER_VALUE;
        assertEquals(1,kIND.size());
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0], value);
        }
    }

    @Test
    public void testASTCheckerVisitorWrongNumberValueNoCroissant() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroWrongNumberValueCroissant.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        ERROR_KIND[] errors = new ERROR_KIND[1];
        errors[0] = ERROR_KIND.WRONG_NUMBER_VALUE;
        assertEquals(1,kIND.size());
        for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
            ERROR_KIND value = entry.getValue();
            assertEquals(errors[0], value);
        }
    }

    @Test
    public void testCheckerUnreachableLine() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroUnreachableLine.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(3,kIND.size());
        ERROR_KIND[] errors = new ERROR_KIND[3];
        errors[0] = ERROR_KIND.UNREACHABLE_LINE;
        errors[1] = ERROR_KIND.UNREACHABLE_LINE;
        errors[2] = ERROR_KIND.UNREACHABLE_LINE;
        for(int i = 0; i < 1; i++) {
            boolean bol = false;
            String previousKey = "";
            for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
                String key = entry.getKey();
                ERROR_KIND value = entry.getValue();
                if (value == errors[i]) {
                    bol = true;
                    previousKey = key;
                }
            }
            assertTrue(bol);
            kIND.remove(previousKey);
        }
    }

    @Test
    public void testCheckerErrors() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroErrors.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String,ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(5,kIND.size());
        ERROR_KIND[] errors = new ERROR_KIND[5];
        errors[0] = ERROR_KIND.UNREACHABLE_LINE;
        errors[1] = ERROR_KIND.WRONG_NUMBER_VALUE;
        errors[2] = ERROR_KIND.EMPTY_STATION_NAME;
        errors[3] = ERROR_KIND.DUPLICATE_STATION_NAME;
        errors[4] = ERROR_KIND.EMPTY_LINE_NAME;
        for(int i = 0; i < 1; i++) {
            boolean bol = false;
            String previousKey = "";
            for (Map.Entry<String, ERROR_KIND> entry : kIND.entrySet()) {
                String key = entry.getKey();
                ERROR_KIND value = entry.getValue();
                if (value == errors[i]) {
                    bol = true;
                    previousKey = key;
                }
            }
            assertTrue(bol);
            kIND.remove(previousKey);
        }
    }

    @Test
    public void testCheckerOK() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        ASTCheckerVisitor visitor = new ASTCheckerVisitor();
        n.accept(visitor);
        Map<String, ERROR_KIND> kIND = visitor.getErrors();
        assertEquals(0, kIND.size());
    }


}
