package be.tribersoft.triber.chat.room.domain.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import be.tribersoft.triber.chat.room.domain.api.Room;

@Entity(name = "room")
public class RoomEntity implements Room {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(length = 256, nullable = false)
	private String name;

	@Column(nullable = false, length = 256)
	private String owner;

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "participant")
	private Set<String> participants = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoomState currentState;

	protected RoomEntity() {
	}

	public RoomEntity(String owner, String name) {
		this.owner = owner;
		this.name = name;
		this.currentState = RoomState.ACTIVE;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public Set<String> getParticipants() {
		return participants;
	}

	@Override
	public String getId() {
		return id;
	}

	public void addParticipant(String participant) {
		participants.add(participant);
	}

	public void removeParticipant(String participant) {
		participants.remove(participant);
	}

	public void inactivate() {
		currentState.inactivate();
		currentState = RoomState.INACTIVE;
	}

	public boolean isActive() {
		return currentState.isActive();
	}

	public boolean isInactive() {
		return currentState.isInactive();
	}

}
