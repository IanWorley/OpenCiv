package me.rhin.openciv.server.listener;

import java.util.ArrayList;

import me.rhin.openciv.server.game.city.City;
import me.rhin.openciv.server.game.religion.PlayerReligion;
import me.rhin.openciv.shared.listener.Event;
import me.rhin.openciv.shared.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CityLooseMajorityReligionListener extends Listener {

	public void onCityLooseMajorityReligion(City city, PlayerReligion oldReligion);

	public static class CityLooseMajorityReligionEvent extends Event<CityLooseMajorityReligionListener> {

		private final Logger LOGGER = LoggerFactory.getLogger(CityLooseMajorityReligionEvent.class);

		private City city;
		private PlayerReligion oldReligion;

		public CityLooseMajorityReligionEvent(City city, PlayerReligion oldReligion) {
			if (oldReligion != null)
				LOGGER.info(city.getName() + " Lost majority - " + oldReligion.getReligionIcon().name());
			this.city = city;
			this.oldReligion = oldReligion;
		}

		@Override
		public void fire(ArrayList<CityLooseMajorityReligionListener> listeners) {
			for (CityLooseMajorityReligionListener listener : listeners) {
				listener.onCityLooseMajorityReligion(city, oldReligion);
			}
		}

		@Override
		public Class<CityLooseMajorityReligionListener> getListenerType() {
			return CityLooseMajorityReligionListener.class;
		}

	}

}
