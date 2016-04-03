package be.tribersoft.triber.chat.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import be.tribersoft.triber.chat.user.domain.api.User;
import be.tribersoft.triber.chat.user.domain.api.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceFindAllTest {

	@InjectMocks
	private DefaultUserService defaultUserService;

	@Mock
	private UserRepository userRepository;
	private Map<String, String> searchParams = new HashMap<>();
	@Mock
	private Pageable pageable;

	private Page<? extends User> page;

	@Test
	public void delegatesToRepository() {
		doReturn(page).when(userRepository).findAll(pageable, searchParams);

		Page<? extends User> result = defaultUserService.findAll(pageable, searchParams);

		assertThat(result).isEqualTo(page);
	}

}
