package me.rhin.openciv.game.city.building.type;

import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.city.building.Building;
import me.rhin.openciv.game.city.specialist.SpecialistContainer;
import me.rhin.openciv.game.heritage.type.mamluks.BazaarHeritage;
import me.rhin.openciv.game.research.type.CurrencyTech;
import me.rhin.openciv.shared.city.SpecialistType;
import me.rhin.openciv.shared.stat.Stat;

public class Market extends Building implements SpecialistContainer {

	public Market(City city) {
		super(city);

		this.statLine.addValue(Stat.GOLD_GAIN, 2);
	}

	@Override
	public float getBuildingProductionCost() {
		return 100;
	}

	@Override
	public float getGoldCost() {
		return 200;
	}

	@Override
	public boolean meetsProductionRequirements() {

		if (city.getPlayerOwner().getHeritageTree().hasStudied(BazaarHeritage.class))
			return false;

		return city.getPlayerOwner().getResearchTree().hasResearched(CurrencyTech.class);
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.BUILDING_MARKET;
	}

	@Override
	public String getName() {
		return "Market";
	}

	@Override
	public int getSpecialistSlots() {
		return 1;
	}

	@Override
	public SpecialistType getSpecialistType() {
		return SpecialistType.MERCHANT;
	}

	@Override
	public void addSpecialist(int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeSpecialist(int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDesc() {
		return "Provides an additional source of \ngold.\n+2 Gold\n+25% Gold in the city";
	}
}
