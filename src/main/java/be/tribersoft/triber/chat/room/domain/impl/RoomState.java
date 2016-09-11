package be.tribersoft.triber.chat.room.domain.impl;

import be.tribersoft.triber.chat.room.domain.api.InvalidRoomStateTransitionException;

public enum RoomState {

	ACTIVE {
		@Override
		public void inactivate() {
		}
	},
	INACTIVE {
		@Override
		public void inactivate() {
			throw new InvalidRoomStateTransitionException();
		}
	};

	public abstract void inactivate();

	public boolean isActive() {
		return this.equals(ACTIVE);
	}

	public boolean isInactive() {
		return this.equals(INACTIVE);
	}

}
