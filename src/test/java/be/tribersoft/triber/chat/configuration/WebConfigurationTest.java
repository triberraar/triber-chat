package be.tribersoft.triber.chat.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

public class WebConfigurationTest {

	private WebConfiguration webConfiguration = new WebConfiguration();

	@Test
	public void pagingIs20ItemsPerPage() {
		List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

		webConfiguration.addArgumentResolvers(resolvers);

		assertThat(resolvers).hasSize(1);
		assertThat(resolvers.get(0)).isInstanceOf(PageableHandlerMethodArgumentResolver.class);
		PageableHandlerMethodArgumentResolver pageableResolver = (PageableHandlerMethodArgumentResolver) resolvers.get(0);
		assertThat(pageableResolver.isFallbackPageable(new PageRequest(0, 20))).isTrue();
	}
}
