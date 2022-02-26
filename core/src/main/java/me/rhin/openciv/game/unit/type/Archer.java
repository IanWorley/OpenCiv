package me.rhin.openciv.game.unit.type;

import java.util.Arrays;
import java.util.List;

import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.map.tile.Tile;
import me.rhin.openciv.game.map.tile.TileType.TileProperty;
import me.rhin.openciv.game.research.type.ArcheryTech;
import me.rhin.openciv.game.research.type.ConstructionTech;
import me.rhin.openciv.game.unit.AttackableEntity;
import me.rhin.openciv.game.unit.RangedUnit;
import me.rhin.openciv.game.unit.UnitItem;
import me.rhin.openciv.game.unit.UnitParameter;

public class Archer extends UnitItem {

	public Archer(City city) {
		super(city);
	}

	public static class ArcherUnit extends RangedUnit {

		public ArcherUnit(UnitParameter unitParameter) {
			super(unitParameter, TextureEnum.UNIT_ARCHER);
		}

		@Override
		public float getMovementCost(Tile prevTile, Tile tile) {
			if (tile.containsTileProperty(TileProperty.WATER))
				return 1000000;
			else
				return tile.getMovementCost(prevTile);
		}

		@Override
		public List<UnitType> getUnitTypes() {
			return Arrays.asList(UnitType.RANGED);
		}

		@Override
		public boolean canUpgrade() {
			return playerOwner.getResearchTree().hasResearched(ConstructionTech.class);
		}
	}

	@Override
	protected float getUnitProductionCost() {
		return 40;
	}

	@Override
	public float getGoldCost() {
		return 100;
	}

	@Override
	public boolean meetsProductionRequirements() {

		//Composite bowman can be built
		if (city.getPlayerOwner().getResearchTree().hasResearched(ConstructionTech.class))
			return false;

		return city.getPlayerOwner().getResearchTree().hasResearched(ArcheryTech.class);
	}

	@Override
	public String getName() {
		return "Archer";
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.UNIT_ARCHER;
	}

	@Override
	public String getDesc() {
		return "A ancient ranged unit.";
	}

	@Override
	public List<UnitType> getUnitItemTypes() {
		return Arrays.asList(UnitType.RANGED);
	}
}