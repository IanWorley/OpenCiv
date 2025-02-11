package me.rhin.openciv.game.city.building.type;

import java.util.Arrays;
import java.util.List;

import me.rhin.openciv.Civilization;
import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.city.building.Building;
import me.rhin.openciv.game.city.wonders.Wonder;
import me.rhin.openciv.game.research.type.BronzeWorkingTech;
import me.rhin.openciv.shared.stat.Stat;
import me.rhin.openciv.shared.stat.StatLine;

public class StatueOfAres extends Building implements Wonder {

	public StatueOfAres(City city) {
		super(city);
	}

	@Override
	public StatLine getStatLine() {
		StatLine statLine = new StatLine();

		statLine.addValue(Stat.HERITAGE_GAIN, 1);

		return statLine;
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.BUILDING_STATUE_OF_ARES;
	}

	@Override
	public boolean meetsProductionRequirements() {
		return !Civilization.getInstance().getGame().getWonders().isBuilt(getClass())
				&& city.getPlayerOwner().getResearchTree().hasResearched(BronzeWorkingTech.class);
	}

	@Override
	public List<String> getDesc() {
		return Arrays.asList("An ancient wonder for agressive expansion.",
				"Provides units +15% combat strength in enemy territory.", "+1 Heritage");
	}

	@Override
	public float getGoldCost() {
		return -1;
	}

	@Override
	public float getBuildingProductionCost() {
		return 185;
	}

	@Override
	public String getName() {
		return "Statue Of Ares";
	}

}
