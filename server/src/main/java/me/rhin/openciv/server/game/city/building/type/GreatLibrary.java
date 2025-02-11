package me.rhin.openciv.server.game.city.building.type;

import me.rhin.openciv.server.Server;
import me.rhin.openciv.server.game.city.City;
import me.rhin.openciv.server.game.city.building.Building;
import me.rhin.openciv.server.game.city.wonders.Wonder;
import me.rhin.openciv.server.game.research.type.WritingTech;
import me.rhin.openciv.shared.city.SpecialistType;
import me.rhin.openciv.shared.stat.Stat;
import me.rhin.openciv.shared.stat.StatLine;

public class GreatLibrary extends Building implements Wonder {

	public GreatLibrary(City city) {
		super(city);
	}

	@Override
	public StatLine getStatLine() {
		StatLine statLine = new StatLine();

		statLine.addValue(Stat.SCIENCE_GAIN, 3);
		statLine.addValue(Stat.HERITAGE_GAIN, 1);
		statLine.addModifier(Stat.SCIENCE_GAIN, 0.1F);

		return statLine;
	}

	@Override
	public void create() {

		for (City city : city.getPlayerOwner().getOwnedCities())
			if (!city.containsBuilding(Library.class))
				city.addBuilding(new Library(city));

		// NOTE: We call this after we modify the cities statline since we send stat
		// update packet here.
		super.create();

		// city.getPlayerOwner().updateOwnedStatlines(false);
	}

	@Override
	public float getBuildingProductionCost() {
		return 185;
	}

	@Override
	public float getGoldCost() {
		return -1;
	}

	@Override
	public boolean meetsProductionRequirements() {
		return city.getPlayerOwner().getResearchTree().hasResearched(WritingTech.class)
				&& !Server.getInstance().getInGameState().getWonders().isBuilt(getClass());
	}

	@Override
	public int getSpecialistSlots() {
		return 2;
	}

	@Override
	public SpecialistType getSpecialistType() {
		return SpecialistType.SCIENTIST;
	}

	@Override
	public String getName() {
		return "Great Library";
	}
}
