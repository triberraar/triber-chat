package be.tribersoft.triber.chat.user.domain.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import com.mysema.query.types.ExpressionUtils;
import com.mysema.query.types.Predicate;

@Named
public class PredicateFactory {

	public Predicate create(Map<String, String> searchParams) {
		Set<Predicate> predicates = new HashSet<>();
		if (searchParams.containsKey("username")) {
			predicates.add(QUserEntity.userEntity.username.containsIgnoreCase(searchParams.get("username")));
		}
		if (searchParams.containsKey("email")) {
			predicates.add(QUserEntity.userEntity.email.containsIgnoreCase(searchParams.get("email")));
		}
		if (searchParams.containsKey("validated")) {
			predicates.add(QUserEntity.userEntity.validated.eq(Boolean.valueOf(searchParams.get("validated"))));
		}
		if (searchParams.containsKey("activated")) {
			predicates.add(QUserEntity.userEntity.validated.eq(Boolean.valueOf(searchParams.get("activated"))));
		}
		return ExpressionUtils.allOf(predicates);
	}

}
