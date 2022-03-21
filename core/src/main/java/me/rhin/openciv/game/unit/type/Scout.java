package me.rhin.openciv.game.unit.type;

import java.util.Arrays;
import java.util.List;

import me.rhin.openciv.asset.TextureEnum;
import me.rhin.openciv.game.city.City;
import me.rhin.openciv.game.map.tile.Tile;
import me.rhin.openciv.game.map.tile.TileType.TileProperty;
import me.rhin.openciv.game.unit.Unit;
import me.rhin.openciv.game.unit.UnitItem;
import me.rhin.openciv.game.unit.UnitParameter;

public class Scout extends UnitItem {

	public Scout(City city) {
		super(city);
	}

	public static class ScoutUnit extends Unit {
		public ScoutUnit(UnitParameter unitParameter) {
			super(unitParameter, TextureEnum.UNIT_SCOUT);
		}

		@Override
		public float getMovementCost(Tile prevTile, Tile tile) {
			if (tile.containsTileProperty(TileProperty.WATER))
				return 1000000;
			else if (tile.getMovementCost(prevTile) > 1 && tile.getMovementCost(prevTile) < 3)
				return 1;
			else
				return tile.getMovementCost(prevTile);
		}

		@Override
		public float getMaxMovement() {
			return 3;
		}

		@Override
		public List<UnitType> getUnitTypes() {
			return Arrays.asList(UnitType.MELEE);
		}

		@Override
		public boolean canUpgrade() {
			return false;
		}
	}

	@Override
	protected float getUnitProductionCost() {
		return 25;
	}

	@Override
	public float getGoldCost() {
		return 100;
	}

	@Override
	public boolean meetsProductionRequirements() {
		return true;
	}

	@Override
	public String getName() {
		return "Scout";
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.UNIT_SCOUT;
	}

	@Override
	public List<String> getDesc() {
		return Arrays.asList("The fundemental scouting unit. Ignores the movement cost of tiles.");
	}

	@Override
	public List<UnitType> getUnitItemTypes() {
		return Arrays.asList(UnitType.MELEE);
	}
}
