package me.rhin.openciv.game.city.building.type;

import me.rhin.openciv.Civilization;
import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.city.building.Building;
import me.rhin.openciv.game.research.type.WheelTech;
import me.rhin.openciv.shared.stat.Stat;
import me.rhin.openciv.shared.stat.StatLine;

public class WaterMill extends Building {

	public WaterMill(City city) {
		super(city);
	}

	@Override
	public StatLine getStatLine() {
		StatLine statLine = new StatLine();

		statLine.addValue(Stat.FOOD_GAIN, 2);
		statLine.addValue(Stat.PRODUCTION_GAIN, 1);
		statLine.addValue(Stat.MAINTENANCE, 2);

		return statLine;
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.BUILDING_WATERMILL;
	}

	@Override
	public float getBuildingProductionCost() {
		return 75;
	}

	@Override
	public boolean meetsProductionRequirements() {
		return city.getTile().isAdjToRiver()
				&& Civilization.getInstance().getGame().getPlayer().getResearchTree().hasResearched(WheelTech.class);
	}

	@Override
	public String getDesc() {
		return "Uses the power of water to\ngenerate food & production.\n+2 Food\n+1 Production\n+2 Maintenance";
	}

	@Override
	public float getGoldCost() {
		return 250;
	}

	@Override
	public String getName() {
		return "Water Mill";
	}
}
