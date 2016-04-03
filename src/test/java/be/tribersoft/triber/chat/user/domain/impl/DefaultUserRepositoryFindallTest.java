package be.tribersoft.triber.chat.user.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;

import com.mysema.query.types.Predicate;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryFindallTest {

	private static final int PAGE_SIZE = 3;
	private static final int PAGE_NUMBER = 2;

	@InjectMocks
	private DefaultUserRepository defaultUserRepository;

	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private PredicateFactory predicateFactory;
	private Map<String, String> searchParams = new HashMap<>();
	@Mock
	private Predicate predicate;
	@Captor
	private ArgumentCaptor<QPageRequest> pageRequestCaptor;
	@Mock
	private Page<UserEntity> page;
	@Mock
	private Pageable pageable;

	@Before
	public void setUp() {
		searchParams.put("key", "value");
		when(predicateFactory.create(searchParams)).thenReturn(predicate);
		when(userJpaRepository.findAll(eq(predicate), pageRequestCaptor.capture())).thenReturn(page);
		when(pageable.getPageNumber()).thenReturn(PAGE_NUMBER);
		when(pageable.getPageSize()).thenReturn(PAGE_SIZE);
	}

	@Test
	public void delegatesToJpaRepository() {

		Page<UserEntity> result = defaultUserRepository.findAll(pageable, searchParams);

		assertThat(result).isEqualTo(page);
		assertThat(pageRequestCaptor.getValue().getPageNumber()).isEqualTo(PAGE_NUMBER);
		assertThat(pageRequestCaptor.getValue().getPageSize()).isEqualTo(PAGE_SIZE);
		assertThat(pageRequestCaptor.getValue().getSort().getOrderSpecifiers()).hasSize(1).contains(QUserEntity.userEntity.username.asc());

	}
}
