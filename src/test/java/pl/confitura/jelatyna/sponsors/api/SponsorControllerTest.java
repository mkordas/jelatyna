package pl.confitura.jelatyna.sponsors.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.confitura.jelatyna.Application;
import pl.confitura.jelatyna.sponsors.SponsorService;
import pl.confitura.jelatyna.sponsors.domain.Sponsor;
import pl.confitura.jelatyna.sponsors.domain.SponsorGroup;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.WebConfig.class, SponsorControllerTest.Config.class})
@WebAppConfiguration
public class SponsorControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private SponsorService sponsorService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createSponsorGroup() throws Exception {
        ResultActions createSponsorGroup = mockMvc.perform(
                post("/sponsorGroup")
                        .content(json("{'name':'platinum', 'label':'Platynowi'}"))
                        .contentType(APPLICATION_JSON));

        createSponsorGroup.andExpect(status().isCreated());
        verify(sponsorService).createSponsorGroup(new SponsorGroup("platinum").withLabel("Platynowi"));
    }

    @Test
    public void createSponsor() throws Exception {
        ResultActions createSponsor = mockMvc.perform(
                post("/sponsor")
                        .content(json("{'name':'Computex','description':'Great company','sponsorGroupName':'platinum'}"))
                        .contentType(APPLICATION_JSON));

        createSponsor.andExpect(status().isCreated());
        verify(sponsorService).createSponsorInGroup(new Sponsor().withName("Computex").withDescription("Great company"), "platinum");
    }

    private String json(String s) {
        return s.replaceAll("'", "\"");
    }

    @Configuration
    @EnableWebMvc
    static class Config {
        @Bean
        public SponsorController sponsorController() {
            return new SponsorController();
        }

        @Bean
        public SponsorService sponsorService() {
            return mock(SponsorService.class);
        }
    }
}