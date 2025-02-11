package me.rhin.openciv.listener;

import java.util.ArrayList;

import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.religion.PlayerReligion;
import me.rhin.openciv.shared.listener.Event;
import me.rhin.openciv.shared.listener.Listener;

public interface CityGainMajorityReligionListener extends Listener {

	public void onCityGainMajorityReligion(City city, PlayerReligion newReligion);

	public static class CityGainMajorityReligionEvent extends Event<CityGainMajorityReligionListener> {

		private City city;
		private PlayerReligion newReligion;

		public CityGainMajorityReligionEvent(City city, PlayerReligion newReligion) {
			this.city = city;
			this.newReligion = newReligion;
		}

		@Override
		public void fire(ArrayList<CityGainMajorityReligionListener> listeners) {
			for (CityGainMajorityReligionListener listener : listeners) {
				listener.onCityGainMajorityReligion(city, newReligion);
			}
		}

		@Override
		public Class<CityGainMajorityReligionListener> getListenerType() {
			return CityGainMajorityReligionListener.class;
		}

	}
}
