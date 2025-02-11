package me.rhin.openciv.game.city.building.type;

import java.util.Arrays;
import java.util.List;

import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.city.building.Building;
import me.rhin.openciv.game.research.type.MasonryTech;
import me.rhin.openciv.shared.stat.Stat;
import me.rhin.openciv.shared.stat.StatLine;

public class Walls extends Building {

	public Walls(City city) {
		super(city);
	}

	@Override
	public StatLine getStatLine() {
		StatLine statLine = new StatLine();

		statLine.addValue(Stat.MAINTENANCE, 1);

		return statLine;
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.BUILDING_WALL;
	}

	@Override
	public boolean meetsProductionRequirements() {
		return city.getPlayerOwner().getResearchTree().hasResearched(MasonryTech.class);
	}

	@Override
	public List<String> getDesc() {
		return Arrays.asList("A basic ancient defensive building.", "+5 City combat strength", "+50 City health");
	}

	@Override
	public float getGoldCost() {
		return 150;
	}

	@Override
	public float getBuildingProductionCost() {
		return 75;
	}

	@Override
	public String getName() {
		return "Walls";
	}

}
