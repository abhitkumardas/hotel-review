package com.adtech.hotelreview;

import com.adtech.hotelreview.model.User;
import com.adtech.hotelreview.model.request.HotelDto;
import com.adtech.hotelreview.model.request.UserAndReviewReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelReviewApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void addDummyHotels() throws Exception {
		List<HotelDto> hotelDtos = Arrays.asList(
				new HotelDto("Orchid", "World Class Quality", true),
				new HotelDto("Oyo 1000", "On Your Own", true),
				new HotelDto("ITC Hotels", "Betterment Living", true)
		);

		for (HotelDto hotelDto : hotelDtos) {
			String platformDtoJson=new ObjectMapper().writeValueAsString(hotelDto);
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/hotel")
					.content(platformDtoJson)
					.contentType("application/json"))
					.andExpect(status().isOk());
			System.out.println(hotelDto.getName() + " Hotel Added Successfully to the System with Load: " + platformDtoJson);
		}
	}

	@Test
	@Order(2)
	public void createUser() throws Exception {
		List<User> usersTobeCreated = Arrays.asList(
				new User("adam","Adam Eve","9987654786", "adam@gow.com"),
				new User("patrick","Patrick Star","9876453422", "patrick@lio.com"),
				new User("trump","Trump Donald","9999999999", "trump@gmail.com"),
				new User("salman","Salman Khan","9898989898", "salman@outlook.com")
		);

		for ( User user : usersTobeCreated){
			String userJson=new ObjectMapper().writeValueAsString(user);
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
					.content(userJson)
					.contentType("application/json"))
					.andExpect(status().isOk());
			System.out.println(user.getName() + " User Created Successfully with Load: " + userJson);
		}
	}

	@Test
	@Order(3)
	public void postSuccessfulReviews() throws Exception{
		List<UserAndReviewReq> reviewReqsToBeSuccess = Arrays.asList(
				new UserAndReviewReq("adam", null, null, "Orchid", 2, "good not great in segment"),
				new UserAndReviewReq("salman", null, null, "ITC Hotels", 5, "marvellous"),
				new UserAndReviewReq("patrick", null, null, "Oyo 1000", 4, "good"),
				new UserAndReviewReq("trump", null, null, "ITC Hotels", 1, "worst"),
				new UserAndReviewReq("adam", null, null, "Oyo 1000", 5, "no 1 in india")
		);

		for (UserAndReviewReq userAndReviewReq : reviewReqsToBeSuccess){
			String userAndReviewJson=new ObjectMapper().writeValueAsString(userAndReviewReq);
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/review")
					.content(userAndReviewJson)
					.contentType("application/json"))
					.andExpect(status().isOk());
			System.out.println(userAndReviewReq.getUserName() + "'s review posted successfully with load: " + userAndReviewJson);
		}
	}
}
