package me.rhin.openciv.server.game.ai.unit;

import java.util.ArrayList;
import java.util.Random;

import me.rhin.openciv.server.Server;
import me.rhin.openciv.server.game.ai.type.BarbarianPlayer;
import me.rhin.openciv.server.game.map.tile.Tile;
import me.rhin.openciv.server.game.map.tile.TileType.TileProperty;
import me.rhin.openciv.server.game.unit.AttackableEntity;
import me.rhin.openciv.server.game.unit.Unit;
import me.rhin.openciv.server.listener.NextTurnListener;

public class BarbarianWarriorAI extends UnitAIOld implements NextTurnListener {

	private Tile campTile;
	private Tile targetTile;
	private AttackableEntity targetEntity;

	public BarbarianWarriorAI(Unit unit, Tile campTile) {
		super(unit);
		this.campTile = campTile;

		Server.getInstance().getEventManager().addListener(NextTurnListener.class, this);
	}

	@Override
	public void onNextTurn() {
		if (!unit.isAlive())
			return;
		// Check if we found a target.

		// TODO: Consider tiles obstructing view.
		findTargets();

		if (targetEntity != null) {
			doTargetPath();
		} else {
			doScoutingPath();
		}
	}

	@Override
	public void clearListeners() {
		Server.getInstance().getEventManager().removeListener(NextTurnListener.class, this);
	}

	private void findTargets() {
		
		//Get all tiles w/ tile overser

		for (Tile tile : unit.getObservedTiles()) {
			AttackableEntity enemyEntity = tile.getEnemyAttackableEntity(unit.getPlayerOwner());
			if (enemyEntity != null && !(enemyEntity.getPlayerOwner() instanceof BarbarianPlayer)) {
				targetEntity = enemyEntity;
				break;
			}
		}
	}

	/**
	 * Track down and attack player units & cities
	 */
	private void doTargetPath() {

		// Change targetUnit if there are closer units.
		for (Tile tile : unit.getStandingTile().getAdjTiles()) {
			AttackableEntity enemyEntity = tile.getEnemyAttackableEntity(unit.getPlayerOwner());
			if (enemyEntity != null && !enemyEntity.equals(targetEntity)) {
				targetEntity = enemyEntity;
			}
		}

		targetTile = targetEntity.getTile();

		ArrayList<Tile> pathTiles = getPathTiles(targetTile);
		Tile pathingTile = stepTowardTarget(pathTiles);

		if (pathingTile == null)
			return;

		// Assume were attacking a unit.
		// FIXME: Account for units in cities.
		AttackableEntity topEntity = pathingTile.getEnemyAttackableEntity(unit.getPlayerOwner());

		if (topEntity != null && topEntity.surviveAttack(unit)) {
			pathingTile = pathTiles.get(1); // Stand outside of enemy unit to attack.
		}

		if (!pathingTile.equals(unit.getStandingTile()))
			moveToTargetTile(pathingTile);

		if (unit.canAttack(topEntity)) {
			unit.attackEntity(topEntity);
		}

		if (targetEntity.getHealth() <= 0 || targetEntity.getPlayerOwner().equals(unit.getPlayerOwner())) {
			targetEntity = null;
			targetTile = null;
		}
	}

	private void doScoutingPath() {

		if (unit.getStandingTile().equals(targetTile)) {
			targetTile = campTile;
		}

		if (unit.getStandingTile().equals(campTile)) {
			targetTile = null;
		}

		// Get an initial random target tile.
		int index = 0;
		while (targetTile == null || targetTile.containsTileProperty(TileProperty.WATER)
				|| targetTile.getMovementCost() > 10) {

			if (index > 30)
				break;
			targetTile = getRandomTargetTile();
			index++;
		}

		if (targetTile == null)
			return;

		ArrayList<Tile> pathTiles = new ArrayList<>();

		index = 0;
		outerloop: while (pathTiles.size() < 1) {

			pathTiles = getPathTiles(targetTile);

			// If there is not a valid path, get another random target.
			if (pathTiles.size() < 1) {
				targetTile = null;
				while (targetTile == null || targetTile.containsTileProperty(TileProperty.WATER)
						|| targetTile.getMovementCost() > 10) {

					if (index > 30)
						break outerloop;
					targetTile = getRandomTargetTile();
					index++;
				}

			}
		}

		if (targetTile == null || pathTiles.size() < 1)
			return;

		Tile pathingTile = stepTowardTarget(pathTiles);

		moveToTargetTile(pathingTile);
	}

	private Tile getRandomTargetTile() {
		Tile targetTile = null;

		Random rnd = new Random();
		int width = Server.getInstance().getMap().getWidth();
		int height = Server.getInstance().getMap().getHeight();
		targetTile = Server.getInstance().getMap().getTiles()[rnd.nextInt(width)][rnd.nextInt(height)];

		return targetTile;
	}
}
