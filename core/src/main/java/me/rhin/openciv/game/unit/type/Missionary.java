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
import me.rhin.openciv.game.unit.actions.type.SpreadReligionAction;

public class Missionary extends UnitItem {

	public Missionary(City city) {
		super(city);
	}

	public static class MissionaryUnit extends Unit {

		public MissionaryUnit(UnitParameter unitParameter) {
			super(unitParameter, TextureEnum.UNIT_MISSIONARY);

			customActions.add(new SpreadReligionAction(this));
		}

		@Override
		public float getMovementCost(Tile prevTile, Tile tile) {
			if (tile.containsTileProperty(TileProperty.WATER))
				return 1000000;
			else
				return tile.getMovementCost(prevTile);
		}

		@Override
		public boolean isUnitCapturable() {
			return true;
		}

		@Override
		public List<UnitType> getUnitTypes() {
			return Arrays.asList(UnitType.SUPPORT);
		}

		@Override
		public boolean canUpgrade() {
			return false;
		}
	}

	@Override
	public float getFaithCost() {
		return 200;
	}

	@Override
	protected float getUnitProductionCost() {
		return -1;
	}

	@Override
	public float getGoldCost() {
		return -1;
	}

	@Override
	public boolean meetsProductionRequirements() {
		// TODO: Support buying prophets from religions you didn't create.
		return city.getPlayerOwner().getReligion().getPickedBonuses().size() > 1
				&& city.getCityReligion().getFollowersOfReligion(city.getPlayerOwner().getReligion()) > 0;
	}

	@Override
	public String getName() {
		return "Missionary";
	}

	@Override
	public TextureEnum getTexture() {
		return TextureEnum.UNIT_MISSIONARY;
	}

	@Override
	public List<String> getDesc() {
		return Arrays.asList(
				"A unit that spreads your religion to other cities. The missionary must be adjacent to the city, and can only spread faith twice.");
	}

	@Override
	public List<UnitType> getUnitItemTypes() {
		return Arrays.asList(UnitType.SUPPORT);
	}
}
