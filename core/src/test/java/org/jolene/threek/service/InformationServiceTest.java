package org.jolene.threek.service;

import org.jolene.threek.CoreTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jolene
 */
public class InformationServiceTest extends CoreTest {

    @Autowired
    private InformationService informationService;

    @Test
    public void testMarkdownClassPath() throws Exception {
        System.out.println(informationService.markdownClassPath("test.md"));
    }
}