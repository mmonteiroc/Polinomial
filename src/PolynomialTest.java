import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pnegre on 30/12/16.
 */
public class PolynomialTest {

    @Test
    public void constructs1() {
        Polynomial p;

        p = new Polynomial(new float[]{1, 5});
        assertEquals("x + 5", p.toString());

        p = new Polynomial(new float[]{0});
        assertEquals("0", p.toString());

        p = new Polynomial();
        assertEquals("0", p.toString());

        p = new Polynomial(new float[]{0, 0, 0});
        assertEquals("0", p.toString());

        p = new Polynomial(new float[]{ 0, 0});
        assertEquals(new Polynomial("0"), p);

        p = new Polynomial(new float[]{-6, 0, 0, 20, -8});
        assertEquals("-6x^4 + 20x - 8", p.toString());
    }

    @Test
    public void constructs2() {
        Polynomial p;

        p = new Polynomial("3x + 5 + 2x^2");
        assertEquals("2x^2 + 3x + 5", p.toString());

        p = new Polynomial("-81x + 9x^9 - 6x^5 - x^90 - 6 + 8x^2");
        assertEquals("-x^90 + 9x^9 - 6x^5 + 8x^2 - 81x - 6", p.toString());

        p = new Polynomial("3x + 5 + 31x^2 - 7");
        assertEquals("31x^2 + 3x - 2", p.toString());

        p = new Polynomial("31x^7 + 3x^7 - 5x - 12x^2 - 15x - 2 - 9 + 20 + 2x^2");
        assertEquals("34x^7 - 10x^2 - 20x + 9", p.toString());

        p = new Polynomial("0");
        assertEquals("0", p.toString());

        p = new Polynomial("3 - 3");
        assertEquals("0", p.toString());

        p = new Polynomial("3x - 3x");
        assertEquals("0", p.toString());

        p = new Polynomial("0x^2 + 6x + 0");
        assertEquals("6x", p.toString());
    }

    @Test
    public void sumes1() {
        Polynomial p1, p2, p3;

        p1 = new Polynomial("2x^2 + 3x - 5");
        p2 = new Polynomial("7x^2 + 10");
        assertEquals("9x^2 + 3x + 5", p1.add(p2).toString());

        p1 = new Polynomial("73x^8 + 3x^4");
        p2 = new Polynomial("-x^2 + 10");
        assertEquals(new Polynomial("73x^8 + 3x^4 - x^2 + 10"), p1.add(p2));

        p1 = new Polynomial("-x^2 + 10");
        p2 = new Polynomial("73x^8 + 3x^4");
        assertEquals("73x^8 + 3x^4 - x^2 + 10", p1.add(p2).toString());

        p1 = new Polynomial("5x^2 + 2x + 10");
        p2 = new Polynomial("8 - 7x^2 + 2x^2");
        assertEquals(new Polynomial("2x + 18"), p1.add(p2));

        p1 = new Polynomial("-2x");
        p2 = new Polynomial("8x^7 + 5x");
        p3 = new Polynomial("-x^2 + 12x - 5x^7");
        assertEquals("3x^7 - x^2 + 15x", p1.add(p2).add(p3).toString());
    }

    @Test
    public void multiplicacions() {
        Polynomial p1, p2;

        p1 = new Polynomial("x^4 - 6x^2 + 8");
        p2 = new Polynomial("-6x^6 - 91x + 12");
        assertEquals("-6x^10 + 36x^8 - 48x^6 - 91x^5 + 12x^4 + 546x^3 - 72x^2 - 728x + 96", p1.mult(p2).toString());

        p1 = new Polynomial("x");
        p2 = new Polynomial("x");
        assertEquals(new Polynomial("x^2"), p1.mult(p2));

        p1 = new Polynomial("0");
        p2 = new Polynomial("x^4 - 7x + 9");
        assertEquals("0", p1.mult(p2).toString());

        p1 = new Polynomial("x + 1");
        p2 = new Polynomial("x - 1");
        assertEquals(new Polynomial("x^2 - 1"), p1.mult(p2));

        p1 = new Polynomial("x + 3");
        p2 = new Polynomial("x + 2");
        assertEquals("x^2 + 5x + 6", p1.mult(p2).toString());

        p1 = new Polynomial("2x + 8");
        p2 = new Polynomial("2x + 8");
        assertEquals(new Polynomial("4x^2 + 32x + 64"), p1.mult(p2));

        p1 = new Polynomial("x");
        p2 = new Polynomial("x + 1");
        assertEquals("x^2 + x", p1.mult(p2).toString());

        p1 = new Polynomial("x + 1");
        p2 = new Polynomial("x + 1");
        assertEquals(new Polynomial("x^2 + 2x + 1"), p1.mult(p2));
    }

    @Test
    public void divisions() {
        Polynomial p1, p2;
        Polynomial[] res;

        p1 = new Polynomial("x^4 - 6x^2 + 8");
        p2 = new Polynomial("x - 1");
        res = p1.div(p2);
        assertEquals(new Polynomial("x^3 + x^2 - 5x - 5"), res[0]);
        assertEquals(new Polynomial("3"), res[1]);

        p1 = new Polynomial("4x^2 + 32x + 64");
        p2 = new Polynomial("2x + 8");
        res = p1.div(p2);
        assertEquals(new Polynomial("2x + 8"), res[0]);
        assertEquals(new Polynomial("0"), res[1]);

        p1 = new Polynomial("-5x^7 + 42x^3 - 9");
        p2 = new Polynomial("x^2 + 1");
        res = p1.div(p2);
        assertEquals(new Polynomial("-5x^5 + 5x^3 + 37x"), res[0]);
        assertEquals(new Polynomial("-37x - 9"), res[1]);
    }

    @Test
    public void rootsRuffini() {
        Polynomial p;

        p = new Polynomial("x^4 + 12x^3 + 11x^2 - 132x + 108");
        assertEqualsFloats(new float[]{-9, -6, 1, 2}, p.roots());

        p = new Polynomial("x^3 - 8x^2 -237x + 36");
        assertEqualsFloats(new float[]{-12f, 0.15114f, 19.84885f}, p.roots());

        p = new Polynomial("x^4 + 1091x^3 + 90632x^2 - 372788x - 4788000");
        assertEqualsFloats(new float[]{-1000, -94.362f, -5.6379f, 9}, p.roots());

        p = new Polynomial("x^7 + 89x^6 - 90x^5 + 10x^2 +890x - 900");
        assertEqualsFloats(new float[]{-90f, -1.5849f, 1f}, p.roots());
    }

    @Test
    public void roots() {
        Polynomial p;

        p = new Polynomial("x - 9");
        assertEqualsFloats(new float[]{9}, p.roots());

        p = new Polynomial("x^2 - 4");
        assertEqualsFloats(new float[]{-2, 2}, p.roots());

        p = new Polynomial("2x^2 + 4x - 30");
        assertEqualsFloats(new float[]{-5, 3}, p.roots());


        // Sense solució
        p = new Polynomial("23x^2 + 90x + 100");
        assertNull(p.roots());

        // Només una solució
        p = new Polynomial("x^2 + 81 + 18x");
        assertEqualsFloats(new float[]{-9}, p.roots());

        // Biquadràtica
        p = new Polynomial("x^4 - 13x^2 + 36");
        assertEqualsFloats(new float[]{-3, -2, 2, 3}, p.roots());

        // Biquadràtica
        p = new Polynomial("x^4 - 10x^2 + 25");
        assertEqualsFloats(new float[]{(float) -2.236, (float) 2.236}, p.roots());

        p = new Polynomial("x^4 - 100");
        assertEqualsFloats(new float[]{-3.1622f, 3.1622f}, p.roots());

        p = new Polynomial("x^3 + 100");
        assertEqualsFloats(new float[]{(float) -4.6415}, p.roots());

        p = new Polynomial("x^8 - 100");
        assertEqualsFloats(new float[]{-1.778f, 1.778f}, p.roots());

        p = new Polynomial("x^13 + 150");
        assertEqualsFloats(new float[]{-1.470f}, p.roots());

        p = new Polynomial("x^12 + 1");
        assertNull(p.roots());





    }

    private void assertEqualsFloats(float[] a1, float[] a2) {
        if (a1.length != a2.length)
            assertEquals(1, 2);
        for (int i = 0; i < a1.length; i++) {
            assertEquals(a1[i], a2[i], 0.001);
        }
    }


}