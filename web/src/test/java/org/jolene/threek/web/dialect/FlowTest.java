package org.jolene.threek.web.dialect;

import org.jolene.threek.web.WebTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jolene
 */
public class FlowTest extends WebTest {

    @Test
    public void first() throws Exception {
        String url = mockMvc.perform(
                get("/")
        )
                .andExpect(status().isFound())
                .andReturn().getResponse().getRedirectedUrl();

        url = mockMvc.perform(
                get(url)
        )
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn().getResponse().getRedirectedUrl();

        mockMvc.perform(
                get(url)
        )
                .andDo(print());
    }
}
