package me.rhin.openciv.server.game.unit.type;

import java.util.Arrays;
import java.util.List;

import me.rhin.openciv.server.game.AbstractPlayer;
import me.rhin.openciv.server.game.city.City;
import me.rhin.openciv.server.game.map.tile.Tile;
import me.rhin.openciv.server.game.map.tile.TileType.TileProperty;
import me.rhin.openciv.server.game.research.type.BronzeWorkingTech;
import me.rhin.openciv.server.game.research.type.CivilServiceTech;
import me.rhin.openciv.server.game.unit.AttackableEntity;
import me.rhin.openciv.server.game.unit.Unit;
import me.rhin.openciv.server.game.unit.UnitItem;
import me.rhin.openciv.server.game.unit.type.Pikeman.PikemanUnit;
import me.rhin.openciv.shared.stat.Stat;

public class Spearman extends UnitItem {

	public Spearman(City city) {
		super(city);
	}

	public static class SpearmanUnit extends Unit {

		public SpearmanUnit(AbstractPlayer playerOwner, Tile standingTile) {
			super(playerOwner, standingTile);
		}

		@Override
		public float getMovementCost(Tile prevTile, Tile tile) {
			if (tile.containsTileProperty(TileProperty.WATER))
				return 1000000;
			else
				return tile.getMovementCost(prevTile);
		}

		@Override
		public float getCombatStrength(AttackableEntity target) {

			float modifier = 1;

			if (target instanceof Unit) {
				Unit targetUnit = (Unit) target;
 
				if (targetUnit.getUnitTypes().contains(UnitType.MOUNTED)) {
					modifier = 1.5F;
				}
			}

			return super.getCombatStrength(target) * modifier;
		}

		@Override
		public List<UnitType> getUnitTypes() {
			return Arrays.asList(UnitType.MELEE);
		}

		@Override
		public Class<? extends Unit> getUpgradedUnit() {
			return PikemanUnit.class;
		}

		@Override
		public boolean canUpgrade() {
			return playerOwner.getResearchTree().hasResearched(CivilServiceTech.class);
		}

		@Override
		public String getName() {
			return "Spearman";
		}

		@Override
		public float getBaseCombatStrength() {
			return 22;
		}
	}

	@Override
	public float getUnitProductionCost() {
		return 56;
	}

	@Override
	public float getGoldCost() {
		return 175;
	}

	@Override
	public boolean meetsProductionRequirements() {

		// If we have pikeman, dont show.
		if (city.getPlayerOwner().getResearchTree().hasResearched(CivilServiceTech.class))
			return false;

		return city.getPlayerOwner().getResearchTree().hasResearched(BronzeWorkingTech.class);
	}

	@Override
	public String getName() {
		return "Spearman";
	}

	@Override
	public List<UnitType> getUnitItemTypes() {
		return Arrays.asList(UnitType.MELEE);
	}

	@Override
	public float getBaseCombatStrength() {
		return 22;
	}
}
