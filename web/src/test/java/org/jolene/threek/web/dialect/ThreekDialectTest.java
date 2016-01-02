package org.jolene.threek.web.dialect;

import org.jolene.threek.web.WebTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Jolene
 */
public class ThreekDialectTest extends WebTest {

    @Autowired
    private ThreekDialect threekDialect;

    @Test
    public void testGetAdditionalExpressionObjects() throws Exception {
        System.out.println(threekDialect);
    }
}