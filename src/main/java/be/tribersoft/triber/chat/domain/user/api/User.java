package be.tribersoft.triber.chat.domain.user.api;

import java.util.Set;

public interface User {

	String getPassword();

	Set<Role> getRoles();

	String getUsername();

}
