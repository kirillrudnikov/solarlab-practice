package com.rudnikov.solarlab;

import com.google.gson.Gson;
import com.rudnikov.solarlab.configuration.InitDatabase;
import com.rudnikov.solarlab.configuration.security.WebSecurityConfiguration;
import com.rudnikov.solarlab.controller.UserController;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.entity.enumerated.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(InitDatabase.class)
@EnableWebMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    @WithUserDetails("evgeniya.2000")
    public void apiAccessDeniedForAnonymousUser_butOnlyForPostPutDeleteRequestsTest() throws Exception {

        User testUser = User.builder()
                .id(6L)
                .username("test.user")
                .email("testik@test.org")
                .phoneNumber("+12345678912")
                .password("dasha123")
                .role(UserRole.USER)
                .build();

        //User testUser = new User("test.user", "testik@test.org", "+12345678911", "fuckyou");

        Gson gson = new Gson();
        String json = gson.toJson(testUser);

        this.mockMvc
                .perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc
                .perform(put("/api/user/update/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc
                .perform(delete("/api/user/delete/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "kirill.rudnikov")
    public void fetchAllUsersFromDatabaseTest() throws Exception {
        this.mockMvc
                .perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("kirill.rudnikov"))
                .andExpect(jsonPath("$[0].email").value("kirillrudnikov811@gmail.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("+79780371649"))
                .andExpect(jsonPath("$[0].role").value("ADMINISTRATOR"))
                .andExpect(jsonPath("$[0].authorities[0].authority").value("ROLE_ADMINISTRATOR"))
                .andExpect(jsonPath("$[0].enabled").value(true))
                .andExpect(jsonPath("$[0].accountNonExpired").value(true))
                .andExpect(jsonPath("$[0].credentialsNonExpired").value(true))
                .andExpect(jsonPath("$[0].accountNonLocked").value(true))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("alexander96"))
                .andExpect(jsonPath("$[1].email").value("alex.gomes@yahoo.com"))
                .andExpect(jsonPath("$[1].phoneNumber").value("+12345678910"))
                .andExpect(jsonPath("$[1].role").value("USER"))
                .andExpect(jsonPath("$[1].authorities[0].authority").value("ROLE_USER"))
                .andExpect(jsonPath("$[1].enabled").value(true))
                .andExpect(jsonPath("$[1].accountNonExpired").value(true))
                .andExpect(jsonPath("$[1].credentialsNonExpired").value(true))
                .andExpect(jsonPath("$[1].accountNonLocked").value(false))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].username").value("ekaterina.boot"))
                .andExpect(jsonPath("$[2].email").value("ekatze.boot@yandex.ru"))
                .andExpect(jsonPath("$[2].phoneNumber").value("+81940124890"))
                .andExpect(jsonPath("$[2].role").value("USER"))
                .andExpect(jsonPath("$[2].authorities[0].authority").value("ROLE_USER"))
                .andExpect(jsonPath("$[2].enabled").value(true))
                .andExpect(jsonPath("$[2].accountNonExpired").value(true))
                .andExpect(jsonPath("$[2].credentialsNonExpired").value(false))
                .andExpect(jsonPath("$[2].accountNonLocked").value(false))

                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].username").value("evgeniya.2000"))
                .andExpect(jsonPath("$[3].email").value("evgeniya.2000year@mail.ru"))
                .andExpect(jsonPath("$[3].phoneNumber").value("+79781677400"))
                .andExpect(jsonPath("$[3].role").value("USER"))
                .andExpect(jsonPath("$[3].authorities[0].authority").value("ROLE_USER"))
                .andExpect(jsonPath("$[3].enabled").value(true))
                .andExpect(jsonPath("$[3].accountNonExpired").value(false))
                .andExpect(jsonPath("$[3].credentialsNonExpired").value(false))
                .andExpect(jsonPath("$[3].accountNonLocked").value(false));

    }

    @Test
    @WithUserDetails(value = "kirill.rudnikov")
    public void fetchUserNumberOneFromDatabaseTest() throws Exception {
        this.mockMvc
                .perform(get("/api/user/get/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("kirill.rudnikov"))
                .andExpect(jsonPath("$.email").value("kirillrudnikov811@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+79780371649"))
                .andExpect(jsonPath("$.role").value("ADMINISTRATOR"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_ADMINISTRATOR"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.accountNonExpired").value(true))
                .andExpect(jsonPath("$.credentialsNonExpired").value(true))
                .andExpect(jsonPath("$.accountNonLocked").value(true));
    }

    @Test
    @WithUserDetails(value = "kirill.rudnikov")
    public void saveUserToDatabaseTest() {

    }

}