//package melopee;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.JUnitRestDocumentation;
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DocumentationRest {
//
//    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() {
//        this.mockMvc =
//            MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation))
//                .build();
//    }
//
//    @Test
//    public void testerRequeteEtDocumenter() throws Exception {
//        mockMvc.perform(
//            RestDocumentationRequestBuilders.get("/profession")
//                .accept(MediaType.APPLICATION_JSON))
//            .andExpect(
//                MockMvcResultMatchers.status()
//                    .isOk())
//            .andDo(MockMvcRestDocumentation.document("profession"));
//    }
//}
