package com.apitest.demo;


import com.apitest.demo.controller.UserController;
import com.apitest.demo.model.User;
import com.apitest.demo.repository.UserRepositoryCustom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepositoryCustom userRepositoryCustom;

    @Test
    public void getUnknownUser() throws Exception {
        mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isNotFound());
    }


    @Test
    public void getValidUser() throws Exception {
        User user = new User("User1", "2000-05-03T00:00:00.000Z", "France", "", "");

        given(userRepositoryCustom.findById(1L)).willReturn(Optional.of(user));
        mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void addValidUser() throws Exception {
        User user = new User("utest", "2004-05-03T00:00:00.000Z", "France", "0605748504", "Male");
        when(userRepositoryCustom.save(user)).thenReturn(user);

        mockMvc.perform(post("/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"utest\",\n" +
                        "    \"birthdate\": \"2004-05-03T00:00:00.000Z\",\n" +
                        "    \"country\": \"France\",\n" +
                        "    \"phone\": \"0605748504\",\n" +
                        "    \"gender\": \"Male\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addUser_checkDateConstraint() throws Exception {
        User user = new User("utest", "2005-05-03T00:00:00.000Z", "France", "0605748504", "Male");
        when(userRepositoryCustom.save(user)).thenReturn(user);

        mockMvc.perform(post("/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"utest\",\n" +
                        "    \"birthdate\": \"2005-05-03T00:00:00.000Z\",\n" +
                        "    \"country\": \"France\",\n" +
                        "    \"phone\": \"0605748504\",\n" +
                        "    \"gender\": \"Male\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Only adult allowed"));

    }

    @Test
    public void addUser_checkCountryConstraint() throws Exception {
        User user = new User("utest", "2005-05-03T00:00:00.000Z", "USA", "0605748504", "Male");
        when(userRepositoryCustom.save(user)).thenReturn(user);

        mockMvc.perform(post("/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"utest\",\n" +
                        "    \"birthdate\": \"2005-05-03T00:00:00.000Z\",\n" +
                        "    \"country\": \"USA\",\n" +
                        "    \"phone\": \"0605748504\",\n" +
                        "    \"gender\": \"Male\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Only French users are accepted"));
    }

    @Test
    public void addUser_checkDateFormat() throws Exception {
        User user = new User("utest", "2005-05-03", "France", "0605748504", "Male");
        when(userRepositoryCustom.save(user)).thenReturn(user);

        mockMvc.perform(post("/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"utest\",\n" +
                        "    \"birthdate\": \"2005-05-03\",\n" +
                        "    \"country\": \"France\",\n" +
                        "    \"phone\": \"0605748504\",\n" +
                        "    \"gender\": \"Male\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Wrong date format: Use yyyy-MM-ddTHH:mm:ss.SSSZ"));
        ;
    }
}