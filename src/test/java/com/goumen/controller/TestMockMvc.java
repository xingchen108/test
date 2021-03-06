package com.goumen.controller;


import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.goumen.BaseTest;
import com.goumen.xiwan.controller.UserController;
import com.goumen.xiwan.entity.UserInfo;
import com.goumen.xiwan.service.UserInfoService;
import com.goumen.xiwan.utils.ReflectionField;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestMockMvc extends BaseTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void before() throws Exception {
        super.setUp();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = "classpath:META-INF-TEST/data/userInfo.xml")
    @DatabaseTearDown(type = DatabaseOperation.DELETE,
            value = {"classpath:META-INF-TEST/data/table.xml"})
    public void testGetUserInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/getUserInfo").param("id", "1"))
                .andExpect(MockMvcResultMatchers.view().name("user/createSuccess"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("================================");
        System.out.println(result.getHandler());
    }
}